package com.example.thrillcast.ui.viewmodels.map

import com.example.thrillcast.ui.common.Takeoff

/**
 * Dataklasse som representerer UI-tilstanden til kartet.
 * Denne klassen holder informasjon om lokasjonene som skal på kartet.
 *
 * @param takeoffs En MutableList som inneholder alle lokasjonene.
 */
data class MapUiState(
    val takeoffs: List<Takeoff>
)
