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
import com.example.thrillcast.ui.viemodels.weather.WeatherViewModel
import java.time.ZonedDateTime

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