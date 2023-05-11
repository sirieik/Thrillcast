import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.thrillcast.ui.screens.mapScreen.HeightWindCard
import com.example.thrillcast.ui.viewmodels.weather.WeatherViewModel
import java.time.ZonedDateTime

@Composable
fun NowPage(weatherViewModel: WeatherViewModel, context: Context) {

    val takeoffUiState = weatherViewModel.takeoffUiState.collectAsState()

    takeoffUiState.value.takeoff?.let {
        weatherViewModel.retrieveForecastWeather(takeoff = it)
    }

    weatherViewModel.retrieveCurrentWeather(takeoff = takeoffUiState.value.takeoff)

    val currentWeatherUiState = weatherViewModel.currentWeatherUiState.collectAsState()


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .height(200.dp),
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

                   NowWeatherCard(
                       windDirection = currentWeatherUiState.value.wind?.direction ?: 0,
                       speed = currentWeatherUiState.value.wind?.speed ?: 0.0,
                       unit = currentWeatherUiState.value.wind?.unit ?: "m/s",
                       gust = currentWeatherUiState.value.wind?.gust ?: 0.0,
                       symbolCode = currentWeatherUiState.value.nowCastObject?.data?.next_1_hours?.summary?.symbol_code ?: "sleetshowersandthunder_polartwilight",
                       temperature = currentWeatherUiState.value.nowCastObject?.data?.instant?.details?.air_temperature
                           ?: 0.0,
                       greenStart = takeoffUiState.value.takeoff?.greenStart ?: 0,
                       greenStop = takeoffUiState.value.takeoff?.greenStop ?: 0,
                       context = context
                   )
                }
                val now = ZonedDateTime.now()
                    .withMinute(0)
                    .withSecond(0)
                    .withNano(0)
                    .withFixedOffsetZone()

                val cardcount = (23 - now.hour)

                items(cardcount){
                    TimeWeatherCard(weatherViewModel = weatherViewModel, context = context, time = now.plusHours((it + 1).toLong()))
                }
            }
        }

        item {
            HeightWindCard(weatherViewModel = weatherViewModel)
        }
    }
}