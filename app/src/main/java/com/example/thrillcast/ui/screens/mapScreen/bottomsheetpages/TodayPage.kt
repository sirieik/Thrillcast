package com.example.thrillcast.ui.screens.mapScreen.bottomsheetpages

import com.example.thrillcast.ui.screens.mapScreen.TimeWeatherCard
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.thrillcast.ui.common.NowWeatherCard
import com.example.thrillcast.ui.screens.mapScreen.HeightWindCard
import com.example.thrillcast.ui.viewmodels.weather.WeatherViewModel
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * @Composable funksjonen viser gjeldende værstatus samt værvarsel for resten av dagen og høydevinddata
 * for de første 7 tilgengelige dataene på en side.
 * Den bruker et LazyColumn layout for å vise dataene.
 *
 * Nåværende vær- og værmeldingsseksjonene inkluderer detaljert værinformasjon som vindretning,
 * hastighet, kast, temperatur og værsymbol.
 * Høydevindstatus gir vinddata på forskjellige høyder.
 *
 * @param weatherViewModel ViewModelen som inneholder UI-status for visning av værinformasjon.
 * @param context Contexten som denne komponerbare funksjonen blir kalt og gjengitt i.
 *
 * @see WeatherViewModel
 * @see NowWeatherCard
 * @see TimeWeatherCard
 * @see HeightWindCard
 *
 * Funksjonen vil vise "Weather data not available." eller "Height wind data not available."
 * dersom det ikke er tilgjengelig værdata i WeatherViewModelen.
 */
@Composable
fun TodayPage(
    weatherViewModel: WeatherViewModel,
    context: Context
) {

    val takeoffUiState = weatherViewModel.takeoffUiState.collectAsState()
    val currentWeatherUiState = weatherViewModel.currentWeatherUiState.collectAsState()
    val forecastWeatherUiState = weatherViewModel.forecastWeatherUiState.collectAsState()
    val heightWindUiState = weatherViewModel.heightWindUiState.collectAsState()

    val currentDate = LocalDate.now()
    val currentDateTime = LocalDateTime.now()

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

                if(currentWeatherUiState.value.wind != null && forecastWeatherUiState.value.locationForecast != null) {
                    item {
                        NowWeatherCard(
                            windDirection = currentWeatherUiState.value.wind?.direction ?: 0,
                            speed = currentWeatherUiState.value.wind?.speed ?: 0.0,
                            unit = currentWeatherUiState.value.wind?.unit ?: "m/s",
                            gust = currentWeatherUiState.value.wind?.gust ?: 0.0,
                            symbolCode = currentWeatherUiState.value.nowCastObject?.data?.next_1_hours?.summary?.symbol_code
                                ?: "sleetshowersandthunder_polartwilight",
                            temperature = currentWeatherUiState.value.nowCastObject?.data?.instant?.details?.air_temperature
                                ?: 0.0,
                            greenStart = takeoffUiState.value.takeoff?.greenStart ?: 0,
                            greenStop = takeoffUiState.value.takeoff?.greenStop ?: 0,
                            context = context
                        )
                    }
                    items(
                        forecastWeatherUiState.value.locationForecast!!.filter {
                            it.time?.toLocalDate() == currentDate && it.time?.toLocalDateTime()!! > currentDateTime
                        }
                    ) {

                        TimeWeatherCard(
                            context = context,
                            time = "${it.time?.hour ?: 99}:${it.time?.minute ?: 9}0",
                            greenStart = takeoffUiState.value.takeoff?.greenStart ?: 0,
                            greenStop = takeoffUiState.value.takeoff?.greenStop ?: 0,
                            symbolCode = it.data?.next_1_hours?.summary?.symbol_code
                                ?: it.data?.next_6_hours?.summary?.symbol_code
                                ?: "sleetshowersandthunder_polartwilight",
                            temperature = it.data?.instant?.details?.air_temperature ?: 0.0,
                            windDirection = it.data?.instant?.details?.wind_from_direction
                                ?: 0.0,
                            windSpeed = it.data?.instant?.details?.wind_speed ?: 0.0
                        )
                    }
                } else {
                    item {
                        Text(text = "Weather data not available.")
                    }
                }
            }
        }
        item {
            if (heightWindUiState.value.windyWindsList != null) {
                HeightWindCard(
                    heightList = listOf("600", "900", "1500", "2000"),
                    windyWindsList = heightWindUiState.value.windyWindsList?.take(7) ?: emptyList()
                )
            } else {
                Text(text = "Height wind data not available.")
            }
        }
    }
}