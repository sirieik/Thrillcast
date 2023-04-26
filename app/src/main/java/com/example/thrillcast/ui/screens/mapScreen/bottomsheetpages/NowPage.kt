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
                items(7){

                    //set(year: Int, month: Int, date: Int, hour: Int, minute: Int, second: Int)

                    val nowDate = Calendar.getInstance()
                    nowDate.set(Calendar.MINUTE, 0)
                    nowDate.set(Calendar.SECOND, 0)

                    val now = ZonedDateTime.now()
                        .withMinute(0)
                        .withSecond(0)
                        .withNano(0)
                        .withFixedOffsetZone()
                        .plusHours((it + 1).toLong())
                    TodayWeatherCard(weatherViewModel = weatherViewModel, context = context, time = now)
                }
            }
        }

        item {
            HeightWindCard(weatherViewModel = weatherViewModel)
        }
    }

}