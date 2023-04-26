import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import com.example.thrillcast.R
import com.example.thrillcast.ui.viemodels.weather.WeatherViewModel

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