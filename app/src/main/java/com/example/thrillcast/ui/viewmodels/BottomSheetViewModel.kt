import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
class BottomSheetViewModel : ViewModel() {
    private val _bottomSheetUiState = MutableStateFlow(ModalBottomSheetValue.Hidden)
    val bottomSheetUiState: StateFlow<ModalBottomSheetValue> = _bottomSheetUiState

    fun expandBottomSheet() {
        viewModelScope.launch {
            _bottomSheetUiState.emit(ModalBottomSheetValue.Expanded)
        }
    }
}