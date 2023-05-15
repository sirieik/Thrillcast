import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
class BottomSheetViewModel : ViewModel() {
    private val _bottomSheetState = MutableStateFlow(ModalBottomSheetValue.Hidden)
    val bottomSheetState: StateFlow<ModalBottomSheetValue> = _bottomSheetState

    fun expandBottomSheet() {
        viewModelScope.launch {
            _bottomSheetState.emit(ModalBottomSheetValue.Expanded)
        }
    }
}