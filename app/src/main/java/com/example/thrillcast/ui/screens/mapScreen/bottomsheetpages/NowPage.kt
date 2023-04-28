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
import com.example.thrillcast.ui.viemodels.weather.WeatherViewModel
import java.time.ZonedDateTime
import java.util.*

@Composable
fun NowPage(weatherViewModel: WeatherViewModel, context: Context) {

    val scrollState = rememberScrollState()

    var currentHeightWindSpeed by remember {
        mutableStateOf("3(2)")
    }
    var currentHeightWindDirection by remember {
        mutableStateOf(Icons.Filled.ArrowBack)
    }

    var weatherTimeList = emptyList<MetObject>()


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .height(200.dp)
        //    .verticalScroll(scrollState)
        ,
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
                    NowWeatherCard(viewModel = weatherViewModel, context = context)
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