package com.example.thrillcast.ui.screens.mapScreen

import MapUiState
import TakeoffUiState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrillcast.data.repositories.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {

    val repo = Repository()

    private val _takoffsUiState = MutableStateFlow(MapUiState(listOf()))

    val takeoffsUiState: StateFlow<MapUiState> = _takoffsUiState.asStateFlow()

    private val _currentTakeoffUiState = MutableStateFlow(TakeoffUiState(null))

    val currentTakeoffUiState: StateFlow<TakeoffUiState> = _currentTakeoffUiState.asStateFlow()
    init {
        retrieveStations()
    }

    //Hente steder rett fra API uten database
    fun retrieveStations() {
        viewModelScope.launch {
            val takeoffs = repo.fetchTakeoffs()

            _takoffsUiState.value = MapUiState(takeoffs)
        }
    }
}