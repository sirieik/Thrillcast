package com.example.thrillcast.ui.viemodels.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel: ViewModel() {

    private val _favoriteUiState = MutableStateFlow(FavoriteUiState(emptyList()))

    val favoritesUiState: StateFlow<FavoriteUiState> = _favoriteUiState.asStateFlow()

    fun retrieveFavorites() {
        viewModelScope.launch {

        }
    }
}