package com.example.thrillcast.ui.viewmodels.map

import com.example.thrillcast.data.repositories.HolfuyRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrillcast.ui.viewmodels.weather.TakeoffUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    val holfuyRepository: HolfuyRepository
) : ViewModel() {


    private val _takoffsUiState = MutableStateFlow(MapUiState(listOf()))

    val takeoffsUiState: StateFlow<MapUiState> = _takoffsUiState.asStateFlow()

    private val _selectedTakeoffUiState = MutableStateFlow(SelectedTakeoffUiState(null))

    val selectedTakeoffUiState: StateFlow<SelectedTakeoffUiState> = _selectedTakeoffUiState.asStateFlow()

    init {
        retrieveStations()
    }

    //Hente steder rett fra API uten database
    private fun retrieveStations() {
        viewModelScope.launch {
            val takeoffs = holfuyRepository.fetchTakeoffs()

            _takoffsUiState.value = MapUiState(takeoffs)
        }
    }

    fun updateChosenTakeoff(takeoff: Takeoff?) {
        viewModelScope.launch {
            _selectedTakeoffUiState.value = SelectedTakeoffUiState(takeoff = takeoff)
        }
    }
}