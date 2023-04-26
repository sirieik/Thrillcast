package com.example.thrillcast.ui.viemodels.map

import HolfuyRepository
import TakeoffUiState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {

    val holfuyRepo = HolfuyRepository()

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
            val takeoffs = holfuyRepo.fetchTakeoffs()

            _takoffsUiState.value = MapUiState(takeoffs)
        }
    }
}