import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.thrillcast.ui.cards.HeightWindCard
import com.example.thrillcast.ui.viemodels.weather.WeatherViewModel
import java.time.ZonedDateTime

@Composable
fun NowPage(weatherViewModel: WeatherViewModel, context: Context) {

    val takeoffUiState = weatherViewModel.takeoffUiState.collectAsState()

    takeoffUiState.value.takeoff?.let {
        weatherViewModel.retrieveForecastWeather(takeoff = it)
    }

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
                    takeoffUiState.value.takeoff?.let {
                        NowWeatherCard(
                            viewModel = weatherViewModel,
                            context = context,
                            takeoff = it
                        )
                    }
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