import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thrillcast.R
import com.example.thrillcast.ui.viemodels.map.Takeoff
import com.example.thrillcast.ui.viemodels.weather.WeatherViewModel

@Composable
fun NowWeatherCard(
    viewModel: WeatherViewModel,
    takeoff: Takeoff,
    context: Context,
    topText: String = stringResource(id = R.string.now)
) {

    viewModel.retrieveCurrentWeather(takeoff)

    val weatherUiState = viewModel.currentWeatherUiState.collectAsState()

    val symbolCode = weatherUiState.value.nowCastObject?.data?.next_1_hours?.summary?.symbol_code

    val unit = weatherUiState.value.wind?.unit ?: ""
    val speed = weatherUiState.value.wind?.speed ?: 0.0
    val gust = weatherUiState.value.wind?.gust ?: 0.0
    val temperature = weatherUiState.value.nowCastObject?.data?.instant?.details?.air_temperature ?: 0

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
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    weatherUiState.value.wind?.direction?.let {
                        WindDirectionWheel(
                            greenStart = takeoff.greenStart,
                            greenStop = takeoff.greenStop,
                            windDirection = it,
                        )
                        Text(
                            text = "$speed($gust) $unit",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
            Column(
                modifier = Modifier.weight(0.33f, true),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    text = topText,
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = "$temperature °C",
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1
                )
            }

            if((symbolCode != null) && symbolCode.isNotEmpty()) {
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