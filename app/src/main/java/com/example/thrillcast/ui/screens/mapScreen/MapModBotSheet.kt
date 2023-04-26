

//import androidx.compose.material.icons.Icons
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thrillcast.R
import com.example.thrillcast.data.met.nowcast.NowCastObject
import com.example.thrillcast.ui.screens.mapScreen.MapScreen
import com.example.thrillcast.ui.screens.mapScreen.MapViewModel
import com.example.thrillcast.ui.screens.mapScreen.SearchBarViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapModBotSheet(
    mapViewModel: MapViewModel = viewModel(),
    weatherViewModel: WeatherViewModel = viewModel(),
    searchBarViewModel: SearchBarViewModel = viewModel(),
    navigateBack: () -> Unit,
    context: Context
) {
    val weatherUiState = weatherViewModel.uiState.collectAsState()
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
                        text = weatherUiState.value.takeoff.name,
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
                            text = {
                                Text(
                                    text = title, maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 20.sp
                                )
                            }
                        )
                    }
                }
                Column(
                    modifier = Modifier.weight(0.7f)
                ) {
                    when (tabState) {
                        0 -> InfoPage(weatherViewModel = weatherViewModel)
                        1 -> NowPage(weatherViewModel = weatherViewModel, context = context)
                        else -> FuturePage(weatherViewModel = weatherViewModel)// it may need changes
                    }
                }
            }

        }
    ) {
        MapScreen(
            coroutineScope = coroutineScope,
            modalSheetState,
            mapViewModel = mapViewModel,
            weatherViewModel = weatherViewModel,
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
fun NowPage(weatherViewModel: WeatherViewModel, context: Context) {

    val scrollState = rememberScrollState()

    var currentHeightWindSpeed by remember {
        mutableStateOf("3(2)")
    }
    var currentHeightWindDirection by remember {
        mutableStateOf(Icons.Filled.ArrowBack)
    }

    var weatherTimeList = emptyList<NowCastObject>()


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .height(200.dp)
        //    .verticalScroll(scrollState)
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            LazyRow(
                contentPadding = PaddingValues(end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(4.dp)
            ) {
                item {
                    NowWeatherCard(viewModel = weatherViewModel, context = context)
                }
                items(7){

                    //set(year: Int, month: Int, date: Int, hour: Int, minute: Int, second: Int)

                    val nowDate = Calendar.getInstance()
                    nowDate.set(Calendar.MINUTE, 0)
                    nowDate.set(Calendar.SECOND, 0)

                    val now = ZonedDateTime.now()
                        .withMinute(0)
                        .withSecond(0)
                        .withNano(0)
                        .withFixedOffsetZone()
                        .plusHours((it + 1).toLong())
                    TodayWeatherCard(weatherViewModel = weatherViewModel, context = context, time = now)
                }
            }
        }

        item {
            HeightWindCard(weatherViewModel = weatherViewModel)
        }
    }

}

@Composable
fun HeightWindCard( weatherViewModel: WeatherViewModel){

    val weatherUiState = weatherViewModel.uiState.collectAsState()

    val buttonTimes = listOf(
        //2,
        5, 8, 11, 14, 17, 20, 23
    )
    val buttonTimestamps: MutableList<Long> = mutableListOf()
    val heights = listOf("600 m", "900m", "1500m", "2000m")

    weatherUiState.value.windyWindsList?.forEach{
        Log.d("Activity", "${Date(it.time)}")
    }

    // Set the time to todays desired times
    buttonTimes.forEach {
        val time = Calendar.getInstance(TimeZone.getTimeZone("GMT+2")).apply {
            set(Calendar.HOUR_OF_DAY, it)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        Log.d("Activity", "$time")
        buttonTimestamps.add(time)
    }


    var selectedHeightIndex by remember {
        mutableStateOf(0)
    }

    var selectedButtonIndex by remember { mutableStateOf(0) }

    var windDirection: Double? = null
    var windSpeed: Double? = null

    try {
        when (selectedHeightIndex) {
            0 -> {
                val windDirAndSpeed = weatherUiState.value.windyWindsList?.filter {
                    it.time == buttonTimestamps[selectedButtonIndex]
                }?.get(0)?.speedDir800h

                if (windDirAndSpeed != null) {
                    windDirection = windDirAndSpeed.second
                    windSpeed = windDirAndSpeed.first
                }
            }
            1 -> {
                val windDirAndSpeed = weatherUiState.value.windyWindsList?.filter {
                    it.time == buttonTimestamps[selectedButtonIndex]
                }?.get(0)?.speedDir850h

                if (windDirAndSpeed != null) {
                    windDirection = windDirAndSpeed.second
                    windSpeed = windDirAndSpeed.first
                }
            }
            2 -> {
                val windDirAndSpeed = weatherUiState.value.windyWindsList?.filter {
                    it.time == buttonTimestamps[selectedButtonIndex]
                }?.get(0)?.speedDir900h

                if (windDirAndSpeed != null) {
                    windDirection = windDirAndSpeed.second
                    windSpeed = windDirAndSpeed.first
                }
            }
            else -> {
                val windDirAndSpeed = weatherUiState.value.windyWindsList?.filter {
                    it.time == buttonTimestamps[selectedButtonIndex]
                }?.get(0)?.speedDir950h

                if (windDirAndSpeed != null) {
                    windDirection = windDirAndSpeed.second
                    windSpeed = windDirAndSpeed.first
                }
            }
        }
    } catch (e: IndexOutOfBoundsException){
        /*
        windDirection = "N/A"
        windSpeed = "N/A"

         */
    }

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
                            text = "$time:00",
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
                    Text(text = stringResource(id =R.string.height))
                    Slider(

                        modifier = Modifier
                            /*
                            .align(Alignment.CenterHorizontally)
                            .width(width = 130.dp)

                                 */
                            //.size(width = 32.dp, height = 240.dp)
                            .rotate(degrees = -90f)
                            .padding(20.dp),

                        value = selectedHeightIndex.toFloat(),
                        onValueChange = { sliderValue ->
                            selectedHeightIndex = sliderValue.toInt()
                        },
                        onValueChangeFinished = {

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
                    if (windDirection != null) {
                        Image(
                            painter = painterResource(id = R.drawable.windarrow),
                            contentDescription = "wind direction",
                            modifier = Modifier
                                .size(100.dp)
                                .rotate((windDirection + 90.0).toFloat())
                        )
                    }
                    Text(

                        text = "$windSpeed m/s"
                    )
                }
            }
        }
    }
}

@Composable
fun NowWeatherCard(viewModel: WeatherViewModel, context: Context) {

    val weatherUiState = viewModel.uiState.collectAsState()

    val symbolCode = weatherUiState.value.nowCastObject?.data?.next_1_hours?.summary?.symbol_code

    ElevatedCard(
        modifier = Modifier
            .height(125.dp)
            .width(350.dp)
    ) {

        Row(

        ) {

            ElevatedCard(
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(0.33f, true)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    weatherUiState.value.wind.direction?.let {
                        WindDirectionWheel(
                            greenStart = weatherUiState.value.takeoff.greenStart,
                            greenStop = weatherUiState.value.takeoff.greenStop,
                            windDirection = it,
                        )
                    }
                    val unit = weatherUiState.value.wind.unit
                    val speed = weatherUiState.value.wind.speed
                    val gust = weatherUiState.value.wind.gust

                    weatherUiState.value.wind.unit?.let {

                        Text(text = "$speed($gust) $unit")
                    }
                }
            }
            Column(
                modifier = Modifier.weight(0.33f, true),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    text = stringResource(id = R.string.now),
                )
                Text(
                    text = "${weatherUiState.value.nowCastObject?.data?.instant?.details?.air_temperature} °C",
                    fontSize = 40.sp
                )
            }

            if(symbolCode != null && symbolCode.isNotEmpty()) {
                Image(
                    modifier = Modifier.weight(0.33f, true),
                    alignment = Alignment.Center,
                    painter = painterResource(
                        id = context.resources.getIdentifier(
                            symbolCode,
                            "drawable",
                            context.packageName
                        )
                    ),
                    contentDescription = symbolCode
                )
            }
        }
    }
}

@Composable
fun TodayWeatherCard(weatherViewModel: WeatherViewModel, context: Context, time: ZonedDateTime) {
    ElevatedCard(
        modifier = Modifier
            .height(125.dp)
            .width(350.dp)
    ) {

        val weatherUiState = weatherViewModel.uiState.collectAsState()

        val windDirection = weatherUiState.value.wind.direction

        val greenStart = weatherUiState.value.takeoff.greenStart
        val greenStop = weatherUiState.value.takeoff.greenStop

        val today = weatherUiState.value.locationForecast?.filter {
            it.time == time
        }

        weatherUiState.value.locationForecast?.forEach {

            Log.d("Date", "APITime:${it.time}")
            Log.d("Date", "MyTime:$time")

        }

        var symbolCode: String? = null

        var temperature: Double? = null

        if (today != null) {
            if (today.isNotEmpty()){
                temperature = today[0].data?.instant?.details?.air_temperature
                symbolCode = today[0].data?.next_1_hours?.summary?.symbol_code
            }
        }



        Row(

        ) {

            Card(
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(0.33f, true),

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
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (windDirection != null) {
                        Image(
                            painter = painterResource(id = R.drawable.windarrow),
                            contentDescription = "wind direction",
                            modifier = Modifier
                                .size(32.dp)
                                .rotate((windDirection + 90).toFloat())
                        )
                    }
                    val unit = weatherUiState.value.wind.unit
                    val speed = weatherUiState.value.wind.speed
                    val gust = weatherUiState.value.wind.gust

                    weatherUiState.value.wind.unit?.let {

                        Text(text = "$speed $unit")
                    }
                }
            }
            Column(
                modifier = Modifier.weight(0.33f, true),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    text = "$time" //"${time.hour}:${time.minute}0",
                )
                Text(
                    text = "$temperature °C",
                    fontSize = 40.sp
                )
            }
            if(symbolCode != null && symbolCode.isNotEmpty()) {
                Image(
                    modifier = Modifier.weight(0.33f, true),
                    alignment = Alignment.Center,
                    painter = painterResource(
                        id = context.resources.getIdentifier(
                            symbolCode,
                            "drawable",
                            context.packageName
                        )
                    ),
                    contentDescription = symbolCode
                )
            }
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

@Composable
fun InfoPage(weatherViewModel: WeatherViewModel) {

    val minCertificate = "PP2/SP2"
    val weatherUiState = weatherViewModel.uiState.collectAsState()
    ElevatedCard(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = androidx.compose.ui.text.SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Minimum certification: ")
                }
                append("$minCertificate")
            },
            fontSize = 22.sp,
            modifier = Modifier.padding(8.dp)
        )
        weatherUiState.value.takeoff.coordinates?.let {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = androidx.compose.ui.text.SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Coordinate: ")
                    }
                    append("${it.latitude}, ${it.longitude}")
                },
                fontSize = 22.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
        weatherUiState.value.takeoff.moh?.let {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = androidx.compose.ui.text.SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("MOH: ")
                    }
                    append("$it")
                },
                fontSize = 22.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}


@Composable
fun FuturePage(weatherViewModel: WeatherViewModel) {
    val weatherUiState = weatherViewModel.uiState.collectAsState()
    Text(text = stringResource(id = R.string.future))
    //HFUiState.value.weatherForecast.next_1_hour.summary.symbol_code
    /**
     * import androidx.compose.foundation.lazy.items
     */
    LazyColumn {
        items(weatherUiState.value.weatherForecast) { weatherForecast ->
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
