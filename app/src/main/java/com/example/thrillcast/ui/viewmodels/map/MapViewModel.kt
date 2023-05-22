package com.example.thrillcast.ui.viewmodels.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrillcast.data.repositories.HolfuyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel klasse som håndterer logikken til kart funksjonaliteten i applikasjonen.
 * Denne klassen henter og oppdaterer tilstanden til takeoff-lokasjonene som skal vises på kartet.
 *
 * @param holfuyRepository En injisert instans av HolfuyRepository som brukes for å hente data for takeoff-lokasjonene.
 */
@HiltViewModel
class MapViewModel @Inject constructor(
    private val holfuyRepository: HolfuyRepository
) : ViewModel() {

    /**
     * Private MutableStateFlow for å håndtere status for UI relatert til lokasjoner som skal vises på kartet.
     */

    private val _takoffsUiState = MutableStateFlow(MapUiState(listOf()))

    /**
     * Public StateFlow som gir les-tilgang til UI statusen for lokasjoner som skal på kartet.
     */
    val takeoffsUiState: StateFlow<MapUiState> = _takoffsUiState.asStateFlow()

    //Henter alle lokasjoner i det viewModelen opprettes
    init {
        retrieveStations()
    }

    /**
     * Henter alle lokasjoner som skal på kartet fra holfuyRepository og legger dem inn i MapUiState.
     */
    private fun retrieveStations() {
        viewModelScope.launch {
            val takeoffs = holfuyRepository.fetchTakeoffs()
            _takoffsUiState.value = MapUiState(takeoffs)
        }
    }

}