package com.example.thrillcast.ui.screens.mapScreen

import HolfuyModel
import MapUiState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrillcast.data.Repository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {

    val repo = Repository()

    //MVP hashMap
    private val takeoffsLocations = hashMapOf(
        "Sundvollen" to LatLng(60.05388889, 10.32250000),
        "Loen Skylift" to LatLng(61.884722, 6.835000),
        "VossHPK Hangur Øst" to LatLng(60.645556, 6.407778)
    )

    private val _uiState = MutableStateFlow(MapUiState(takeoffs = takeoffsLocations))

    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    private val HolfuyClient = HolfuyModel()

    init {
        retrieveStations()
    }

    //Hente steder rett fra API uten database
    fun retrieveStations() {
        viewModelScope.launch {
            val takeoffs = repo.fetchStationLatLngAndNames()

            _uiState.value = MapUiState(takeoffs)
        }
    }
}