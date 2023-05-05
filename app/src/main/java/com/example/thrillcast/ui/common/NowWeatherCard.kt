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
import com.example.thrillcast.R
import com.example.thrillcast.ui.viemodels.map.Takeoff
import com.example.thrillcast.ui.viemodels.weather.WeatherViewModel

@Composable
fun NowWeatherCard(
    viewModel: WeatherViewModel,
    takeoff: Takeoff,
    context: Context
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
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            /*ElevatedCard(
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(0.33f, true)
                    .fillMaxHeight()
            ) {*/
            Column(
                modifier = Modifier.fillMaxHeight(),
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
            Column(
                modifier = Modifier.weight(0.33f, true),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    text = stringResource(id = R.string.now),
                    style = MaterialTheme.typography.labelMedium,
                    //TODO - dette gjør at det ikke fucker seg med flere linjer på favorite screen
                    maxLines = 1
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