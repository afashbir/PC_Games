package com.ewide.test.andri.ui.favourites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ewide.test.andri.domain.model.Game
import com.ewide.test.andri.domain.repository.GameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val gameRepository: GameRepository
) : ViewModel() {

    val uiState = MutableLiveData<FavouritesModelUiState>()

    fun load() {
        viewModelScope.launch {
            uiState.postValue(FavouritesModelUiState.Loading)

            val data = gameRepository.getFavourites()
            handleLoad(data)
        }
    }

    private fun handleLoad(data: List<Game>) {
        if (data.isEmpty()) {
            uiState.postValue(FavouritesModelUiState.EmptyResult)
        } else {
            uiState.postValue(FavouritesModelUiState.Success(data))
        }
    }

    fun unFavorite(item: Game) {
        viewModelScope.launch {
            gameRepository.unFavorite(item)

            val data = gameRepository.getFavourites()
            handleLoad(data)
        }
    }
}

sealed interface FavouritesModelUiState {
    object Loading : FavouritesModelUiState

    object EmptyResult : FavouritesModelUiState

    data class Success(val data: List<Game>) : FavouritesModelUiState
}