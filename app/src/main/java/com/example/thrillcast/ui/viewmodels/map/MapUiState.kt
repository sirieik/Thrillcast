package com.example.thrillcast.ui.viewmodels.map

open class MapUiState(
    val takeoffs: List<Takeoff>
)

open class SelectedTakeoffUiState(
    val takeoff: Takeoff?
)