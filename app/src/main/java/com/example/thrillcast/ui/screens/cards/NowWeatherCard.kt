import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.ElevatedCard
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