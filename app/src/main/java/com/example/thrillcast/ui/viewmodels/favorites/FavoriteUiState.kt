package com.example.thrillcast.ui.viewmodels.favorites

import com.example.thrillcast.ui.common.Takeoff

/**
 * Dataklasse som representerer UI-tilstanden til favorittfunksjonaliteten.
 * Denne klassen holder informasjon om den aktuelle listen over favorittlokasjoner,
 * og en boolean som indikerer om en spesifikk lokasjon er en favoritt.
 *
 * @param favoriteList En MutableList som inneholder alle favorittlokasjonene.
 * @param isFavorite En Boolean som indikerer om en spesifikk lokasjon er en favoritt.
 */
data class FavoriteUiState(
    val favoriteList: MutableList<Takeoff?>,
    val isFavorite: Boolean
)