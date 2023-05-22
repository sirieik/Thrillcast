package com.example.thrillcast.ui.viewmodels

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * En ViewModel for å administrere brukergrensesnittstaten til en BottomSheet.
 *
 * Denne klassen er ansvarlig for å kontrollere tilstanden til en bottom sheet-komponent. Tilstanden til bottom sheet
 * er eksponert via `bottomSheetUiState` StateFlow-objektet som sender ut verdier av typen `ModalBottomSheetValue`.
 *
 * Denne klassen er annotert med `@OptIn(ExperimentalMaterialApi::class)`-annoteringen som betyr at den bruker API som er
 * merket som eksperimentelt i Material Design-biblioteket.
 */
@OptIn(ExperimentalMaterialApi::class)
class BottomSheetViewModel : ViewModel() {

    private val _bottomSheetUiState = MutableStateFlow(ModalBottomSheetValue.Hidden)
    val bottomSheetUiState: StateFlow<ModalBottomSheetValue> = _bottomSheetUiState

    /**
     * Setter verdien av `_bottomSheetUiState` til `ModalBottomSheetValue.Expanded`,
     * og utvider dermed bottom sheet.
     */
    fun expandBottomSheet() {
        viewModelScope.launch {
            _bottomSheetUiState.emit(ModalBottomSheetValue.Expanded)
        }
    }
}