import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.thrillcast.ui.viemodels.weather.WeatherViewModel

@Composable
fun FuturePage(weatherViewModel: WeatherViewModel) {
    val weatherUiState = weatherViewModel.uiState.collectAsState()
    val buttonDays = listOf(
        "Mon" , "Tue" , "Wed", "Thur", "Fri", "Sat" , "Sun"
    )
    var selectedButtonIndex by remember{ mutableStateOf(0) }
    //Text(text = "Future")
    //HFUiState.value.weatherForecast.next_1_hour.summary.symbol_code
    /**
     * import androidx.compose.foundation.lazy.items
     */

    LazyRow(
        modifier = Modifier.fillMaxWidth()

            .padding(4.dp),
        contentPadding = PaddingValues(6.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ){
        itemsIndexed(buttonDays) { index, day ->
            val isSelected = index == selectedButtonIndex
            ElevatedButton(
                onClick = {
                    selectedButtonIndex = index
                }
            ) {
                Text(
                    text = "$day" ,
                    style = MaterialTheme.typography.body1.copy(
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                )
            }
        }
    }
    //fix LazyColum
    LazyColumn (modifier = Modifier.fillMaxWidth()){
        items(weatherUiState.value.weatherForecast) { weatherForecast ->
            val time = weatherForecast.time.toLocalTime()
            val text = weatherForecast.data?.next_1_hours?.summary?.symbol_code ?: ""
            val air_temp = weatherForecast.data?.instant?.details?.air_temperature ?: 0.0
            val wind_speed = weatherForecast.data?.instant?.details?.wind_speed ?: 0.0

            ElevatedCard {
                Row {
                    Text(text = "${time}       ${air_temp}C         ${wind_speed}m/s        ${text}")
                }

            }
            //Text(text = "${time}       ${air_temp}C         ${wind_speed}m/s        ${text}")
        }
    }
}