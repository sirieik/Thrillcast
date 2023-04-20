package com.example.thrillcast.ui.screens.mapScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SearchBarViewModel: ViewModel() {
    var state by mutableStateOf(SearchBarState())

    fun onAction(userAction: UserAction) {
        when(userAction) {
            UserAction.CloseActionClicked -> {
                state = state.copy(
                    isSearchBarVisible = false
                )
            }
            UserAction.SearchIconClicked -> {
                state = state.copy(
                    isSearchBarVisible = true
                )
            }
        }
    }
}

sealed class UserAction {
    object SearchIconClicked: UserAction()
    object CloseActionClicked: UserAction()
}

data class SearchBarState(
    val isSearchBarVisible: Boolean = false

)