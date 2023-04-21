

//import androidx.compose.material.icons.Icons
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thrillcast.R
import com.example.thrillcast.ui.screens.mapScreen.MapScreen
import com.example.thrillcast.ui.screens.mapScreen.MapViewModel
import com.example.thrillcast.ui.screens.mapScreen.SearchBarViewModel
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapModBotSheet(
    mapViewModel: MapViewModel = viewModel(),
    holfuyWeatherViewModel: HolfuyWeatherViewModel = viewModel(),
    searchBarViewModel: SearchBarViewModel = viewModel(),
    navigateBack: () -> Unit
) {
    val HFUiState = holfuyWeatherViewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )

    val tabList = listOf("Info", "Today", "Future")

    var tabState by remember {
        mutableStateOf(0)
    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetContent = {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.1f, true)
                ) {
                    Text(
                        text = HFUiState.value.takeoff.name,
                        style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.W900),
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .padding(start = 20.dp)
                            .weight(0.8f, true),
                        color = Color.Black
                    )
                    IconButton(
                        onClick = { coroutineScope.launch { modalSheetState.hide() } },
                        modifier = Modifier
                            .weight(0.2f, true)
                            .padding(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close info sheet"
                        )
                    }
                }
                TabRow(selectedTabIndex = tabState) {
                    tabList.forEachIndexed { index, title ->
                        Tab(
                            selected = tabState == index,
                            onClick = { tabState = index },
                            text = { Text(text = title, maxLines = 1, overflow = TextOverflow.Ellipsis) }
                        )
                    }
                }
                Column(
                    modifier = Modifier.weight(0.7f)
                ) {
                    when (tabState) {
                        0 -> InfoPage(holfuyWeatherViewModel = holfuyWeatherViewModel)
                        1 -> NowPage(holfuyWeatherViewModel = holfuyWeatherViewModel)
                        else -> FuturePage(holfuyWeatherViewModel = holfuyWeatherViewModel)// it may need changes
                    }
                }
            }

        }
    ) {
        MapScreen(
            coroutineScope = coroutineScope,
            modalSheetState,
            mapViewModel = mapViewModel,
            holfuyWeatherViewModel = holfuyWeatherViewModel,
            searchBarViewModel = searchBarViewModel,
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

    var weatherTimeList = emptyList<NowCastObject>()

    LazyRow( ) {
        itemsIndexed(weatherTimeList) { index, weatherData ->
            if (index == 0) {
                NowWeatherCard(viewModel = holfuyWeatherViewModel)
            }
            else {

            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .height(200.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            NowWeatherCard(viewModel = holfuyWeatherViewModel)

            HeightWindCard()
        }
    }

}

@Composable
fun HeightWindCard(){

    val buttonTimes = listOf(
        "02:00", "05:00", "08:00", "11:00",
        "14:00", "17:00", "20:00", "23:00"
    )

    val heights = listOf("600 m", "900m", "1500m", "2000m")

    var currentHeightWindSpeed by remember {
        mutableStateOf("3(2)")
    }
    var currentHeightWindDirection by remember {
        mutableStateOf(Icons.Filled.ArrowBack)
    }

    var selectedHeightIndex by remember {
        mutableStateOf(0)
    }

    var windDirection = 0
    var windSpeed = 100

    var selectedButtonIndex by remember { mutableStateOf(0) }

    ElevatedCard(
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxSize()
            .padding(6.dp),
        shape = RoundedCornerShape(6.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .weight(0.2f, true)
                    .padding(6.dp),
                text = "Height wind",
                fontSize = 20.sp
            )
            LazyRow(
                modifier = Modifier
                    .weight(0.2f, true)
                    .padding(4.dp),
                contentPadding = PaddingValues(6.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top
            ) {
                itemsIndexed(buttonTimes) { index, time ->
                    val isSelected = index == selectedButtonIndex
                    ElevatedButton(
                        onClick = {
                            selectedButtonIndex = index
                        }
                    ) {
                        Text(
                            text = time,
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.6f)
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.3f, true)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var sliderValue by remember {
                        mutableStateOf(0f)
                    }
                    //Spacer(modifier = Modifier.height(100.dp))
                    Text(text = "Height:")
                    Slider(

                        modifier = Modifier
                            /*
                            .align(Alignment.CenterHorizontally)
                            .width(width = 130.dp)

                                 */
                            .rotate(degrees = -90f)
                            .padding(20.dp),

                        value = selectedHeightIndex.toFloat(),
                        onValueChange = { sliderValue ->
                            selectedHeightIndex = sliderValue.toInt()
                        },
                        onValueChangeFinished = {
                            when (sliderValue) {
                                0f -> {
                                    //windHeight = "600 moh"
                                    currentHeightWindSpeed = "4(5)"
                                    currentHeightWindDirection = Icons.Filled.ArrowBack
                                }
                                1f -> {
                                    //windHeight = "900 moh"
                                    currentHeightWindSpeed = "8(2)"
                                    currentHeightWindDirection = Icons.Filled.ArrowForward
                                }
                                2f -> {
                                    //windHeight = "1500 moh"
                                    currentHeightWindSpeed = "4(2)"
                                    currentHeightWindDirection = Icons.Filled.ArrowDropDown
                                }
                                else -> {
                                    //windHeight = "2000 moh"
                                    currentHeightWindSpeed = "1(2)"
                                    currentHeightWindDirection = Icons.Filled.ArrowForward
                                }
                            }
                            // this is called when the user completed selecting the value
                            Log.d("MainActivity", "sliderValue = $sliderValue")
                        },
                        valueRange = 0f..(heights.size - 1).toFloat(),
                        steps = heights.size - 2
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = heights[selectedHeightIndex])
                }

                Column(
                    modifier = Modifier
                        .weight(0.7f, true)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.windarrow),
                        contentDescription = "wind direction",
                        modifier = Modifier
                            .size(100.dp)
                            .rotate(windDirection.toFloat())
                    )
                    Text(
                        text = "$windSpeed m/s"
                    )
                }
            }
        }
    }
}

@Composable
fun NowWeatherCard(viewModel: HolfuyWeatherViewModel) {
    ElevatedCard(

    ) {

        val degrees = 0

        Row(

        ) {
            val HFUiState = viewModel.uiState.collectAsState()

            ElevatedCard(
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(0.33f, true)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
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

                    HFUiState.value.wind.unit?.let {

                        Text(text = "$speed($gust) $unit")
                    }
                }
            }
            Column(
                modifier = Modifier.weight(0.33f, true),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    text = "Now",
                )
                Text(
                    text = "$degrees °C",
                    fontSize = 40.sp
                )
            }
            Image(
                modifier = Modifier.weight(0.33f, true),
                alignment = Alignment.Center,
                painter = painterResource(
                    id = R.drawable.clearsky_day), contentDescription = "weather"
            )
        }
    }
}

@Composable
fun TodayWeatherCard(viewModel: HolfuyWeatherViewModel, weatherData: NowCastObject) {
    ElevatedCard(

    ) {

        val HFUiState = viewModel.uiState.collectAsState()

        val windDirection = HFUiState.value.wind.direction

        val greenStart = HFUiState.value.takeoff.greenStart
        val greenStop = HFUiState.value.takeoff.greenStop

        val temperature = 0

        Row(

        ) {

            Card(
                modifier = Modifier.aspectRatio(1f),
                //Here we set the color as green if the winddirection falls inside the holfuywheel
                //If not we set it as red
                backgroundColor =
                if (windDirection?.let { isDegreeBetween(it, greenStart, greenStop) } == true) {
                    Color.Green
                } else {
                    Color.Red
                }
            ) {
                Column(

                ) {
                    if (windDirection != null) {
                        Image(
                            painter = painterResource(id = R.drawable.windarrow),
                            contentDescription = "wind direction",
                            modifier = Modifier
                                .size(32.dp)
                                .rotate(windDirection.toFloat())
                        )
                    }
                    val unit = HFUiState.value.wind.unit
                    val speed = HFUiState.value.wind.speed
                    val gust = HFUiState.value.wind.gust

                    HFUiState.value.wind.unit?.let {

                        Text(text = "$speed $unit")
                    }
                }
            }
            Text( "$temperature °C")
            Image(
                painter = painterResource(
                    id = R.drawable.clearsky_day), contentDescription = "weather"
            )
        }
    }
}

@Composable
fun ChangeDayButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    ElevatedButton(
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
    Text(text = "Tomorrow")
    //HFUiState.value.weatherForecast.next_1_hour.summary.symbol_code
    /**
     * import androidx.compose.foundation.lazy.items
     */
    LazyColumn {
        items(HFUiState.value.weatherForecast) { weatherForecast ->
            val time = weatherForecast.time.toLocalTime()
            val text = weatherForecast.data?.next_1_hours?.summary?.symbol_code ?: ""
            val air_temp = weatherForecast.data?.instant?.details?.air_temperature ?: 0.0
            val wind_speed = weatherForecast.data?.instant?.details?.wind_speed ?: 0.0

            Text(text = "${time}       ${air_temp}C         ${wind_speed}m/s        ${text}")
        }
    }
}

fun isDegreeBetween(value: Int, min: Int, max: Int): Boolean {
    val valueRadians = Math.toRadians(value.toDouble())
    val minRadians = Math.toRadians(min.toDouble())
    val maxRadians = Math.toRadians(max.toDouble())

    return if (minRadians <= maxRadians) {
        valueRadians in minRadians..maxRadians
    } else {
        valueRadians >= minRadians || valueRadians <= maxRadians
    }
}
