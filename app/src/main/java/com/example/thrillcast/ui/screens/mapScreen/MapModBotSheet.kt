
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thrillcast.ui.screens.mapScreen.MapScreen
import com.example.thrillcast.ui.screens.mapScreen.MapViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapModBotSheet(
    mapViewModel: MapViewModel = viewModel(),
    holfuyWeatherViewModel: HolfuyWeatherViewModel = viewModel(),
    navigateBack: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetContent = {
            Column(
                //...
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch { modalSheetState.hide() }
                    }
                ) {
                    Spacer(modifier = Modifier.height(200.dp))
                    Text(text = "Hide Sheet")
                }
            }
        }
    ) {
        MapScreen(coroutineScope = coroutineScope, modalSheetState, viewModel = mapViewModel, navigateBack)
    }
}