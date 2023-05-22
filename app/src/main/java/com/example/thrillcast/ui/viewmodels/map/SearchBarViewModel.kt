package com.example.thrillcast.ui.viewmodels.map

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * En ViewModel-klasse som håndterer tilstanden til søkefeltet på kartet.
 * Denne klassen oppdaterer tilstanden basert på brukerens handlinger og inneholder
 * en funksjon for å håndtere disse handlingene.
 */
class SearchBarViewModel: ViewModel() {
    var state by mutableStateOf(SearchBarState())


    /**
     * Denne funksjonen håndterer brukerens handlinger og oppdaterer tilstanden til søkefeltet tilsvarende.
     *
     * @param userAction Brukerens handling som enten er å klikke på søkeikonet og åpne søkefeltet
     * eller å lukke det.
     */

    fun onAction(userAction: UserAction) {
        state = when(userAction) {
            UserAction.CloseActionClicked -> {
                state.copy(
                    isSearchBarVisible = false
                )
            }
            UserAction.SearchIconClicked -> {
                state.copy(
                    isSearchBarVisible = true
                )
            }
        }
    }
}

/**
 * En forseglet klasse som representerer brukerens handlinger.
 * Disse handlingene er enten å klikke på søkeikonet og åpne søkefeltet
 * eller å lukke det.
 */
sealed class UserAction {
    object SearchIconClicked: UserAction()
    object CloseActionClicked: UserAction()
}

/**
 * En dataklasse som representerer tilstanden til søkefeltet. Denne klassen har en
 * boolsk verdi som angir om søkefeltet er synlig eller ikke.
 *
 * @param isSearchBarVisible En boolsk verdi som angir om søkefeltet er synlig eller ikke. Standardverdien er false.
 */
data class SearchBarState(
    val isSearchBarVisible: Boolean = false
)