package com.example.thrillcast.ui.screens.cards
import WeatherForecast
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thrillcast.R
import com.example.thrillcast.ui.viemodels.weather.WeatherViewModel

@Composable
fun FutureWeatherCard(weatherForecast : WeatherForecast, context : Context) {
    val symbolCode = weatherForecast.data.next_1_hours?.summary?.symbol_code
        ?: weatherForecast.data.next_6_hours?.summary?.symbol_code
        ?: ""

    val air_temp = weatherForecast.data.instant.details.air_temperature ?: 0.0
    val time = weatherForecast.time.toLocalTime()
    val wind_spped = weatherForecast.data.instant.details.wind_speed
    val wind_direction = weatherForecast.data.instant.details.wind_from_direction

        ElevatedCard (modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp))
                    .padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text("$time", fontSize = 20.sp)

                Text(
                    text = "$air_temp °C",
                    fontSize = 20.sp
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (wind_direction != null) {
                        Image(
                            painter = painterResource(id = R.drawable.windarrow),
                            contentDescription = "wind direction",
                            modifier = Modifier
                                .size(20.dp)
                                .rotate((wind_direction - 90.0).toFloat())
                        )
                    Text("${wind_spped} m/s", fontSize = 20.sp)
                    }
                }




                if (symbolCode != null && symbolCode.isNotEmpty()) {
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





