import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.thrillcast.R
import com.example.thrillcast.data.datamodels.Wind
import com.example.thrillcast.ui.common.WindCondition
import com.example.thrillcast.ui.theme.FlightGreen
import com.example.thrillcast.ui.theme.Red60
import com.example.thrillcast.ui.viewmodels.weather.WeatherViewModel
import java.time.ZonedDateTime

@Composable
fun TimeWeatherCard(weatherViewModel: WeatherViewModel, context: Context, time: ZonedDateTime) {
    //fun TimeWeatherCard(temperature: , windDirection, windSpeed, symbolCode)
    ElevatedCard(
        modifier = Modifier
            .height(125.dp)
            .width(350.dp)
            .clip(RoundedCornerShape(4.dp))
            .padding(4.dp)
    ) {

        val weatherUiState = weatherViewModel.forecastWeatherUiState.collectAsState()
        val takeoffUiState = weatherViewModel.takeoffUiState.collectAsState()

        val greenStart = takeoffUiState.value.takeoff?.greenStart ?: 0
        val greenStop = takeoffUiState.value.takeoff?.greenStop ?: 0

        val today = weatherUiState.value.locationForecast?.filter {
            it.time?.toLocalDateTime() == time.toLocalDateTime()
        }

        var symbolCode: String? = null

        var temperature: Double? = null

        var windDirection: Double? = null
        var windSpeed: Double? = null



        temperature = today?.firstOrNull()?.data?.instant?.details?.air_temperature ?: 0.0
        symbolCode = today?.firstOrNull()?.data?.next_1_hours?.summary?.symbol_code
            ?: today?.firstOrNull()?.data?.next_6_hours?.summary?.symbol_code
                    ?: ""


        windDirection = today?.firstOrNull()?.data?.instant?.details?.wind_from_direction ?: 0.0
        windSpeed = today?.firstOrNull()?.data?.instant?.details?.wind_speed ?: 0.0





        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Card(
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(0.33f, true)
                    .fillMaxSize()
                    .padding(6.dp),

                //Here we set the color as green if the winddirection falls inside the holfuywheel
                //If not we set it as red
                backgroundColor = checkWindConditions(
                    windDirection = windDirection, windSpeed = windSpeed,
                    greenStart = greenStart, greenStop = greenStop
                ).color
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,



                ) {
                    if (windDirection != null) {
                        Image(
                            painter = painterResource(id = R.drawable.windarrow),
                            contentDescription = "wind direction",
                            modifier = Modifier
                                .size(32.dp)
                                .rotate((windDirection + 90).toFloat())//Jeg trodde at -90, men kan

                        )
                    }

                    Text(
                        text = "$windSpeed m/s",
                        style = MaterialTheme.typography.bodyMedium
                    )

                }
            }
            Column(
                modifier = Modifier.weight(0.33f, true),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    text = "${time.hour}:${time.minute}0",
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = "$temperature°C",
                    style = MaterialTheme.typography.bodyLarge,
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