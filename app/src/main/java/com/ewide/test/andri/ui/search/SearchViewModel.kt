package com.ewide.test.andri.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewide.test.andri.domain.model.Game
import com.ewide.test.andri.domain.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: GameRepository
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
                    uiState.postValue(SearchModelUiState.Success(data))
                }
            } catch (e: Exception) {
                uiState.postValue(SearchModelUiState.Error(e))
            }
        }
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
}

sealed interface SearchModelUiState {
    object Loading : SearchModelUiState
    object EmptyResult : SearchModelUiState
    data class Error(val throwable: Throwable) : SearchModelUiState
    data class Success(val data: List<Game>) : SearchModelUiState
}