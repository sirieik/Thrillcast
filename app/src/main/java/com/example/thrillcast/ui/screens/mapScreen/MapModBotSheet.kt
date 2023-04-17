
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thrillcast.R
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

    var sliderValue by remember {
        mutableStateOf(800f)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(0.3f, true),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val HFUiState = holfuyWeatherViewModel.uiState.collectAsState()
            HFUiState.value.wind.direction?.let {
                WindDirectionWheel(
                    greenStart = HFUiState.value.takeoff.greenStart,
                    greenStop = HFUiState.value.takeoff.greenStop,
                    windDirection = it,
                )
            }
            val unit = HFUiState.value.wind.unit
            val speed = HFUiState.value.wind.speed
            val gust = HFUiState.value.wind.gust
            val windyspeed = HFUiState.value.windSpeed

            HFUiState.value.wind.unit?.let {
                Text(text = "$speed ($gust) $unit")
            }

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(0.4f, true)
        ) {
            Text(text = "Rainy 5°C")

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(0.3f, true)
        ) {
            //Spacer(modifier = Modifier.height(100.dp))
            Slider(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(width = 130.dp)
                    .rotate(degrees = -90f),
                value = sliderValue,
                onValueChange = { sliderValue_ ->
                    sliderValue = sliderValue_
                },
                onValueChangeFinished = {

                    /*
                    TO DO
                    hent data for valgt høyde
                     */


                    // this is called when the user completed selecting the value
                    Log.d("MainActivity", "sliderValue = $sliderValue")
                },
                valueRange = 800f..950f,
                steps = 2
            )
            Text(text = sliderValue.toInt().toString() + " moh")
        }
    }
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

@Preview
@Composable
fun prevPage() {
    NowPage(holfuyWeatherViewModel = viewModel())
}

@Composable
private fun HeightSlider() {

    var sliderValue by remember {
        mutableStateOf(0f)
    }

    Slider(
        modifier = Modifier
            .width(width = 130.dp)
            .rotate(degrees = -90f),
        value = sliderValue,
        onValueChange = { sliderValue_ ->
            sliderValue = sliderValue_
        },
        onValueChangeFinished = {
            // this is called when the user completed selecting the value
            Log.d("MainActivity", "sliderValue = $sliderValue")
        },
        valueRange = 800f..950f,
        steps = 4
    )
}