package com.ewide.test.andri.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewide.test.andri.domain.model.Game
import com.ewide.test.andri.domain.model.SortPref
import com.ewide.test.andri.domain.repository.GameRepository
import com.ewide.test.andri.domain.repository.SortRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: GameRepository,
    private val sortRepository: SortRepository
) : ViewModel() {

    val uiState = MutableLiveData<SearchModelUiState>()

    fun searchGame(title: String) {
        viewModelScope.launch {
            try {
                uiState.postValue(SearchModelUiState.Loading)

                val data = searchRepository.searchGames(title)

                if (data.isEmpty()) {
                    uiState.postValue(SearchModelUiState.EmptyResult)
                } else {
                    val sortPref = sortRepository.sortPref()
                    val sortedData = sort(sortPref, data)

                    uiState.postValue(SearchModelUiState.Success(sortedData, sortPref))
                }
            } catch (e: Exception) {
                uiState.postValue(SearchModelUiState.Error(e))
            }
        }
    }

    private fun sort(
        sortPref: SortPref,
        data: List<Game>
    ): List<Game> {
        val sortedData = when (sortPref) {
            SortPref.ASC -> data.sortedBy { it.title }
            SortPref.DESC -> data.sortedByDescending { it.title }
        }
        return sortedData
    }

    fun updateLovedStatus(item: Game) {
        viewModelScope.launch {
            if (item.isLoved) {
                searchRepository.favorite(item)
            } else {
                searchRepository.unFavorite(item)
            }
        }
    }

    fun sortResult() {
        viewModelScope.launch {
            val currentSortPref = sortRepository.sortPref()
            val newSortPref = if (currentSortPref == SortPref.ASC) SortPref.DESC
            else SortPref.ASC

            val latestData = (uiState.value as? SearchModelUiState.Success)?.data
            latestData?.run {
                val sortedData = sort(newSortPref, this)
                uiState.postValue(SearchModelUiState.Success(sortedData, newSortPref))
            }

            sortRepository.storeSortPref(newSortPref)
        }
    }
}

sealed interface SearchModelUiState {
    object Loading : SearchModelUiState
    object EmptyResult : SearchModelUiState
    data class Error(val throwable: Throwable) : SearchModelUiState
    data class Success(val data: List<Game>, val sortPref: SortPref) : SearchModelUiState
}