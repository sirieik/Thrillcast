package com.example.thrillcast.ui.viewmodels.favorites

import androidx.lifecycle.ViewModel
import com.example.thrillcast.ui.common.Takeoff
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * ViewModel for håndtering av favorittlokasjoner.
 * Denne klassen gir funksjonaliteten for å legge til og fjerne favorittlokasjoner,
 * og for å sjekke om en spesifikk lokasjon er markert som favoritt.
 *
 * @constructor Oppretter en ny FavoriteViewModel med en tom liste av favorittlokasjoner.
 */
@HiltViewModel
class FavoriteViewModel @Inject constructor() : ViewModel() {

    /**
     * Private MutableStateFlow for å håndtere status for UI relatert til favorittlokasjoner.
     * Denne MutableStateFlow inneholder en liste over favorittlokasjoner og en boolean
     * som indikerer om en spesifikk lokasjon er favoritt eller ikke.
     */
    private val _favoriteUiState = MutableStateFlow(FavoriteUiState(mutableListOf(), false))

    /**
     * Public StateFlow som gir les-tilgang til UI statusen for favorittlokasjoner.
     */
    val favoriteUiState: StateFlow<FavoriteUiState> = _favoriteUiState.asStateFlow()

    /**
     * Endrer favorittstatusen for en gitt lokasjon.
     * Hvis lokasjonen allerede er en favoritt, fjernes den fra favoritter.
     * Hvis den ikke er en favoritt, legges den til i favoritter.
     *
     * @param takeoff Lokasjonen som skal endre sin favorittstatus.
     */
    fun toggleFavorite(takeoff: Takeoff) {
        if (_favoriteUiState.value.favoriteList.contains(takeoff)) {
            removeFavorite(takeoff)
        } else {
            addFavorite(takeoff)
        }
        isFavorite(takeoff)
    }


    /**
     * Legger til en lokasjon i favorittlisten.
     *
     * @param takeoff Lokasjonen som skal legges til i favorittlisten.
     */
    private fun addFavorite(takeoff: Takeoff) {
        _favoriteUiState.value.favoriteList.add(takeoff)
    }

    /**
     * Fjerner en lokasjon fra favorittlisten.
     *
     * @param takeoff Lokasjonen som skal fjernes fra favorittlisten.
     */
    private fun removeFavorite(takeoff: Takeoff) {
        _favoriteUiState.value.favoriteList.remove(takeoff)
    }

    /**
     * Sjekker om en gitt lokasjon er en favoritt og oppdaterer _favoriteUiState tilsvarende.
     *
     * @param takeoff Lokasjonen som skal sjekkes.
     */
    fun isFavorite(takeoff: Takeoff?) {
        val isFavorite = _favoriteUiState.value.favoriteList.contains(takeoff)
        _favoriteUiState.value = _favoriteUiState.value.copy(isFavorite = isFavorite)
    }

}