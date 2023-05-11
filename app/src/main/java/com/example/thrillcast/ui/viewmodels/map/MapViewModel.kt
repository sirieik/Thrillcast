package com.example.thrillcast.ui.viewmodels.map

import com.example.thrillcast.data.repositories.HolfuyRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrillcast.ui.viewmodels.weather.TakeoffUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewModel() : ViewModel() {

    val holfuyRepo = HolfuyRepository()

    private val _takoffsUiState = MutableStateFlow(MapUiState(listOf()))

    val takeoffsUiState: StateFlow<MapUiState> = _takoffsUiState.asStateFlow()

    private val _currentTakeoffUiState = MutableStateFlow(TakeoffUiState(null))

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