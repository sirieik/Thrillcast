import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.thrillcast.ui.theme.Yellow
import com.example.thrillcast.ui.viewmodels.weather.WeatherViewModel
import java.time.LocalDate

@Composable
fun FuturePage(weatherViewModel: WeatherViewModel, context : Context) {

    val takeoffUiState = weatherViewModel.takeoffUiState.collectAsState()

    /*
    takeoffUiState.value.takeoff?.let {
        weatherViewModel.retrieveForecastWeather(takeoff = it)
    }

     */

    val weatherUiState = weatherViewModel.forecastWeatherUiState.collectAsState()

    val buttonDays = listOf(
        1L, 2L, 3L, 4L, 5L, 6L, 7L
    ).map { LocalDate.now().plusDays(it) }
    var selectedButtonIndex by remember { mutableStateOf(0) }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        contentPadding = PaddingValues(6.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        itemsIndexed(buttonDays) { index, day ->
            val isSelected = index == selectedButtonIndex
            ElevatedButton(
                onClick = {
                    selectedButtonIndex = index
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(Yellow),

            ) {
                Text(
                    text = day.dayOfWeek.name.take(3),
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                )
            }
        }
    }
    LazyColumn(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        weatherUiState.value.locationForecast?.let { forecastList ->
            items(forecastList.filter { it.time?.toLocalDate() == buttonDays[selectedButtonIndex] }) { it ->

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