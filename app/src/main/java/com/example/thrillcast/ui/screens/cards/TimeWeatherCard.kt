import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thrillcast.R
import com.example.thrillcast.ui.theme.FlightGreen
import com.example.thrillcast.ui.theme.GreenDark
import com.example.thrillcast.ui.theme.Red60
import com.example.thrillcast.ui.viemodels.weather.WeatherViewModel
import java.time.ZonedDateTime

@Composable
fun TimeWeatherCard(weatherViewModel: WeatherViewModel, context: Context, time: ZonedDateTime) {
    ElevatedCard(
        modifier = Modifier
            .height(125.dp)
            .width(350.dp)
    ) {

        val weatherUiState = weatherViewModel.uiState.collectAsState()

        val greenStart = weatherUiState.value.takeoff.greenStart
        val greenStop = weatherUiState.value.takeoff.greenStop

        val today = weatherUiState.value.locationForecast?.filter {
            it.time.toLocalDateTime() == time.toLocalDateTime()
        }

        weatherUiState.value.locationForecast?.forEach {

            Log.d("Date", "APITime:${it.time.toLocalDateTime()}")
            Log.d("Date", "MyTime:${time.toLocalDateTime()}")

        }

        var symbolCode: String? = null

        var temperature: Double? = null

        var windDirection: Double? = null
        var windSpeed: Double? = null

        if (today != null && today.isNotEmpty()) {

                temperature = today[0].data?.instant?.details?.air_temperature
                symbolCode = today[0].data?.next_1_hours?.summary?.symbol_code
                windDirection = today[0].data.instant.details.wind_from_direction
                windSpeed = today[0].data.instant.details.wind_speed

        }



        Row(

        ) {

            Card(
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(0.33f, true)
                    .fillMaxSize()
                    .padding(6.dp),

                //Here we set the color as green if the winddirection falls inside the holfuywheel
                //If not we set it as red
                backgroundColor =
                if (windDirection?.let { isDegreeBetween(it, greenStart, greenStop) } == true) {
                    FlightGreen
                } else {
                    Red60
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

                    weatherUiState.value.wind.unit?.let {

                        Text(text = "$windSpeed m/s")
                    }
                }
            }
            Column(
                modifier = Modifier.weight(0.33f, true),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    text = "${time.hour}:${time.minute}0",
                )
                Text(
                    text = "$temperature °C",
                    fontSize = 40.sp,
                    maxLines = 1
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