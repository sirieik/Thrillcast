

//import androidx.compose.material.icons.Icons
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thrillcast.ui.screens.mapScreen.MapScreen
import com.example.thrillcast.ui.screens.mapScreen.MapViewModel
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapModBotSheet(
    mapViewModel: MapViewModel = viewModel(),
    holfuyWeatherViewModel: HolfuyWeatherViewModel = viewModel(),
    navigateBack: () -> Unit
) {
    val HFUiState = holfuyWeatherViewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    var currentSheet by remember { mutableStateOf<SheetPage>(SheetPage.Info) }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetContent = {

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = HFUiState.value.takeoff.name,
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.W900),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .padding(start = 20.dp),
                    color = Color.Black
                )
                IconButton(
                    onClick = { coroutineScope.launch { modalSheetState.hide() } },
                    modifier = Modifier.align(Alignment.End).padding(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close info sheet"
                    )
                }
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
                        onClick = { currentSheet = SheetPage.Future }
                    )
                }

                when (currentSheet) {
                    is SheetPage.Info -> InfoPage(holfuyWeatherViewModel = holfuyWeatherViewModel)
                    is SheetPage.Now -> NowPage(holfuyWeatherViewModel = holfuyWeatherViewModel)
                    else -> FuturePage(holfuyWeatherViewModel = holfuyWeatherViewModel)// it may need changes
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

    var currentHeightWindSpeed by remember {
        mutableStateOf("3(2)")
    }
    var currentHeightWindDirection by remember {
        mutableStateOf(Icons.Filled.ArrowBack)
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

                Text(text = "$speed $unit")
            }

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(0.4f, true)
        ) {
            Icon(
                painter = painterResource(id = com.example.thrillcast.R.drawable.rainy),
                "rainy",
                modifier = Modifier.size(50.dp)
                )
            Text(text = "Rainy 5°C")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Wind: " + currentHeightWindSpeed +" m/s")
            Icon(currentHeightWindDirection, "arrow")

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(0.3f, true)
                .padding(6.dp)
        ) {
            var sliderValue by remember {
                mutableStateOf(0f)
            }
            var windHeight by remember { mutableStateOf("Surface") }
            //Spacer(modifier = Modifier.height(100.dp))
            Slider(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .width(width = 130.dp)
                    .rotate(degrees = -90f)
                    .padding(6.dp),
                value = sliderValue,
                onValueChange = { sliderValue_ ->
                    sliderValue = sliderValue_
                },
                onValueChangeFinished = {

                    /*
                    TO DO
                    hent data for valgt høyde
                    950h = 600 moh
                    900h = 900 moh
                    850h = 1500 moh
                    800h = 2000 moh
                     */
                    when(sliderValue) {
                        0f -> {
                            windHeight = "Surface"
                            currentHeightWindSpeed = "4(5)"
                            currentHeightWindDirection = Icons.Filled.ArrowBack
                        }
                        1f -> {
                            windHeight = "600 moh"
                            currentHeightWindSpeed = "8(2)"
                            currentHeightWindDirection = Icons.Filled.ArrowForward
                        }
                        2f -> {
                            windHeight = "900 moh"
                            currentHeightWindSpeed = "4(2)"
                            currentHeightWindDirection = Icons.Filled.ArrowDropDown
                        }
                        3f -> {
                            windHeight = "1500 moh"
                            currentHeightWindSpeed = "1(2)"
                            currentHeightWindDirection = Icons.Filled.ArrowForward
                        }
                        else -> {
                            windHeight = "2000 moh"
                            currentHeightWindSpeed = "2(2)"
                            currentHeightWindDirection = Icons.Filled.ArrowBack
                        }
                    }


                    // this is called when the user completed selecting the value
                    Log.d("MainActivity", "sliderValue = $sliderValue")
                },
                valueRange = 0f..4f,
                steps = 3
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = windHeight)

        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun InfoPage(holfuyWeatherViewModel: HolfuyWeatherViewModel) {

    val HFUiState = holfuyWeatherViewModel.uiState.collectAsState()
        Text(text = "PP2")
    HFUiState.value.takeoff.coordinates?.let{
        Text(text = "Coordinate: ${it.latitude}, ${it.longitude}")

    }
    HFUiState.value.takeoff.moh?.let{
        Text(text = "MOH: $it")
    }

    //Text(text = "INFO")
}
@Composable
//Maybe we can change to "Tomorrow" instead of "future"? for the buttoom-name??
fun FuturePage(holfuyWeatherViewModel: HolfuyWeatherViewModel) {
    val HFUiState = holfuyWeatherViewModel.uiState.collectAsState()
    Text(text = "FUTURE")
    //HFUiState.value.weatherForecast.next_1_hour.summary.symbol_code
    /**
     * import androidx.compose.foundation.lazy.items
     */
    LazyColumn {
        items(HFUiState.value.weatherForecast) { weatherForecast ->
            val time = weatherForecast.time.toLocalTime()
            val text = weatherForecast.data?.next_1_hours?.summary?.symbol_code ?: ""
            val air_temp = weatherForecast.data?.instant?.details?.air_temperature?:0.0
            val wind_speed =weatherForecast.data?.instant?.details?.wind_speed?:0.0

            Text(text = "${time}       ${air_temp}C         ${wind_speed}m/s        ${text}")
        }
    }


}

@Preview
@Composable
fun prevPage() {
    NowPage(holfuyWeatherViewModel = viewModel())
}

