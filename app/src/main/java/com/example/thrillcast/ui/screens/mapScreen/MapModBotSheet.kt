
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thrillcast.ui.screens.mapScreen.MapScreen
import com.example.thrillcast.ui.screens.mapScreen.MapViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.StateFlow
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
    val HFUiState = holfuyWeatherViewModel.uiState.collectAsState()
    var currentSheet by remember { mutableStateOf<SheetPage>(SheetPage.Info) }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetContent = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ChangePageButton(
                        text = "Info",
                        isSelected = currentSheet == SheetPage.Info,
                        onClick = { currentSheet = SheetPage.Info }
                    )
                    ChangePageButton(
                        text = "Now",
                        isSelected = currentSheet == SheetPage.Now,
                        onClick = { currentSheet = SheetPage.Now }
                    )
                    ChangePageButton(
                        text = "Future",
                        isSelected = currentSheet == SheetPage.Future,
                        onClick = {currentSheet = SheetPage.Future}
                    )
                    IconButton(
                        onClick = { coroutineScope.launch { modalSheetState.hide() } },
                        //modifier = Modifier.align(Alignment.End)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close info sheet"
                        )
                    }
                }

                when(currentSheet) {
                    is SheetPage.Info -> InfoPage(holfuyWeatherViewModel = holfuyWeatherViewModel)
                    is SheetPage.Now -> NowPage(holfuyWeatherViewModel = holfuyWeatherViewModel)
                    else -> FuturePage()
                }
            }
        }
    ) {
        MapScreen(
            coroutineScope = coroutineScope,
            modalSheetState,
            mapViewModel = mapViewModel,
            holfuyWeatherViewModel = holfuyWeatherViewModel,
            navigateBack
        )
    }
}

@Composable
fun ChangePageButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body1.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        )
    }
}

@Composable
fun NowPage(holfuyWeatherViewModel: HolfuyWeatherViewModel) {
    val HFUiState = holfuyWeatherViewModel.uiState.collectAsState()
    HFUiState.value.wind.direction?.let {
        WindDirectionWheel(
            greenStart = HFUiState.value.takeoff.greenStart,
            greenStop = HFUiState.value.takeoff.greenStop,
            windDirection = it,
        )
    }
    HFUiState.value.wind.unit?.let { Text(text = it) }
}

@Composable
fun InfoPage(holfuyWeatherViewModel: HolfuyWeatherViewModel) {
    val HFUiState = holfuyWeatherViewModel.uiState.collectAsState()
    HFUiState.value.takeoff.coordinates?.let{
        Text(text = "Coordinate: ${it.latitude}, ${it.longitude}")

    }
    HFUiState.value.takeoff.moh?.let{
        Text(text = "MOH: $it")
    }

    //Text(text = "INFO")
}
@Composable
fun FuturePage() {
    Text(text = "FUTURE")
}

