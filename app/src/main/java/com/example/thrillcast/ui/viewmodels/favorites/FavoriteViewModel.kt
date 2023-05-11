package com.example.thrillcast.ui.viewmodels.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrillcast.data.datamodels.Wind
import com.example.thrillcast.data.repositories.HolfuyRepository
import com.example.thrillcast.data.repositories.MetRepository
import com.example.thrillcast.ui.viewmodels.map.Takeoff
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    val holfuyRepository: HolfuyRepository,
) : ViewModel() {

    private val _favoriteUiState = MutableStateFlow(FavoriteUiState(mutableListOf()))
    val favoriteUiState: StateFlow<FavoriteUiState> = _favoriteUiState.asStateFlow()

    fun addFavorite(takeoff: Takeoff) {
        _favoriteUiState.value.favoriteList.add(takeoff)
    }

    fun removeFavorite(takeoff: Takeoff) {
        _favoriteUiState.value.favoriteList.remove(takeoff)
    }

}