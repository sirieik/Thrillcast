import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thrillcast.R
import com.example.thrillcast.ui.viemodels.weather.WeatherViewModel
import java.util.*

@Composable
fun HeightWindCard( weatherViewModel: WeatherViewModel){

    val weatherUiState = weatherViewModel.uiState.collectAsState()

    val buttonTimes = listOf(
        //2,
        5, 8, 11, 14, 17, 20, 23
    )
    val buttonTimestamps: MutableList<Long> = mutableListOf()
    val heights = listOf("600 m", "900m", "1500m", "2000m")

    weatherUiState.value.windyWindsList?.forEach{
        Log.d("Activity", "${Date(it.time)}")
    }

    // Set the time to todays desired times
    buttonTimes.forEach {
        val time = Calendar.getInstance(TimeZone.getTimeZone("GMT+2")).apply {
            set(Calendar.HOUR_OF_DAY, it)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        Log.d("Activity", "$time")
        buttonTimestamps.add(time)
    }


    var selectedHeightIndex by remember {
        mutableStateOf(0)
    }

    var selectedButtonIndex by remember { mutableStateOf(0) }

    var windDirection: Double? = null
    var windSpeed: Double? = null

    try {
        when (selectedHeightIndex) {
            0 -> {
                val windDirAndSpeed = weatherUiState.value.windyWindsList?.filter {
                    it.time == buttonTimestamps[selectedButtonIndex]
                }?.get(0)?.speedDir800h

                if (windDirAndSpeed != null) {
                    windDirection = windDirAndSpeed.second
                    windSpeed = windDirAndSpeed.first
                }
            }
            1 -> {
                val windDirAndSpeed = weatherUiState.value.windyWindsList?.filter {
                    it.time == buttonTimestamps[selectedButtonIndex]
                }?.get(0)?.speedDir850h

                if (windDirAndSpeed != null) {
                    windDirection = windDirAndSpeed.second
                    windSpeed = windDirAndSpeed.first
                }
            }
            2 -> {
                val windDirAndSpeed = weatherUiState.value.windyWindsList?.filter {
                    it.time == buttonTimestamps[selectedButtonIndex]
                }?.get(0)?.speedDir900h

                if (windDirAndSpeed != null) {
                    windDirection = windDirAndSpeed.second
                    windSpeed = windDirAndSpeed.first
                }
            }
            else -> {
                val windDirAndSpeed = weatherUiState.value.windyWindsList?.filter {
                    it.time == buttonTimestamps[selectedButtonIndex]
                }?.get(0)?.speedDir950h

                if (windDirAndSpeed != null) {
                    windDirection = windDirAndSpeed.second
                    windSpeed = windDirAndSpeed.first
                }
            }
        }
    } catch (e: IndexOutOfBoundsException){
        /*
        windDirection = "N/A"
        windSpeed = "N/A"

         */
    }

    ElevatedCard(
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxSize()
            .padding(6.dp),
        shape = RoundedCornerShape(6.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                    //.weight(0.2f, true),
                text = "Height wind",
                fontSize = 20.sp
            )
            LazyRow(
                modifier = Modifier,
                  //  .weight(0.2f, true),
                contentPadding = PaddingValues(6.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top
            ) {
                itemsIndexed(buttonTimes) { index, time ->
                    val isSelected = index == selectedButtonIndex
                    ElevatedButton(
                        modifier = Modifier.padding(6.dp),
                        onClick = {
                            selectedButtonIndex = index
                        }
                    ) {
                        Text(
                            text = "$time:00",
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.6f)
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.3f, true)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var sliderValue by remember {
                        mutableStateOf(0f)
                    }
                    //Spacer(modifier = Modifier.height(100.dp))
                    Text(text = stringResource(id = R.string.height))
                    Slider(

                        modifier = Modifier
                            /*
                            .align(Alignment.CenterHorizontally)
                            .width(width = 130.dp)

                                 */
                            //.size(width = 32.dp, height = 240.dp)
                            .rotate(degrees = -90f)
                            .padding(20.dp),

                        value = selectedHeightIndex.toFloat(),
                        onValueChange = { sliderValue ->
                            selectedHeightIndex = sliderValue.toInt()
                        },
                        onValueChangeFinished = {

                            // this is called when the user completed selecting the value
                            Log.d("MainActivity", "sliderValue = $sliderValue")
                        },
                        valueRange = 0f..(heights.size - 1).toFloat(),
                        steps = heights.size - 2
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = heights[selectedHeightIndex])
                }

                Column(
                    modifier = Modifier
                        .weight(0.7f, true)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (windDirection != null) {
                        Image(
                            painter = painterResource(id = R.drawable.windarrow),
                            contentDescription = "wind direction",
                            modifier = Modifier
                                .size(100.dp)
                                .rotate((windDirection + 90.0).toFloat())
                        )
                    }
                    Text(

                        text = "$windSpeed m/s"
                    )
                }
            }
        }
    }
}