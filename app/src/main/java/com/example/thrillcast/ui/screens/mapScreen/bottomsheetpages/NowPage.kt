import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.thrillcast.ui.screens.mapScreen.HeightWindCard
import com.example.thrillcast.ui.viewmodels.weather.WeatherViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZonedDateTime

@Composable
fun NowPage(weatherViewModel: WeatherViewModel, context: Context) {

    val takeoffUiState = weatherViewModel.takeoffUiState.collectAsState()
    val currentWeatherUiState = weatherViewModel.currentWeatherUiState.collectAsState()
    val forecastWeatherUiState = weatherViewModel.forecastWeatherUiState.collectAsState()

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
                forecastWeatherUiState.value.locationForecast?.let {forecastList ->
                    items(
                        forecastList.filter {
                            it.time?.toLocalDate() == LocalDate.now() && it.time?.toLocalDateTime()!! > LocalDateTime.now()
                        }
                    ) {
                        TimeWeatherCard(
                            context = context,
                            time = "${it.time?.hour ?: 99}:${it.time?.minute ?: 9}0",
                            greenStart = takeoffUiState.value.takeoff?.greenStart ?: 0,
                            greenStop = takeoffUiState.value.takeoff?.greenStop ?: 0,
                            symbolCode = it.data?.next_1_hours?.summary?.symbol_code
                                ?: it.data?.next_6_hours?.summary?.symbol_code
                                ?: "sleetshowersandthunder_polartwilight",
                            temperature = it.data?.instant?.details?.air_temperature ?: 0.0,
                            windDirection = it.data?.instant?.details?.wind_from_direction ?: 0.0,
                            windSpeed = it.data?.instant?.details?.wind_speed ?: 0.0
                        )
                    }
                }
            }
        }
        item {
            HeightWindCard(weatherViewModel = weatherViewModel)
        }
    }
}