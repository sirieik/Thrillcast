package com.example.thrillcast.ui.screens.mapScreen.bottomsheetpages

import com.example.thrillcast.ui.screens.mapScreen.TimeWeatherCard
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thrillcast.ui.theme.DarkerYellow
import com.example.thrillcast.ui.theme.Yellow
import com.example.thrillcast.ui.viewmodels.weather.WeatherViewModel
import java.time.LocalDate

/**
 * @Composable funksjon som representerer FuturePage-skjermen i bottomSheetet på kartskjermen.
 *
 * Denne skjermen presenterer værprognosen for de neste 7 dagene, og lar brukeren veksle
 * mellom forskjellige dager og se den tilgjengelige værdataen for hver dag i detalj.
 *
 * @param weatherViewModel En ViewModel som gir vær- og takeoff-tilstandene for visningen.
 * @param context Android Context av kalleren.
 *
 * Brukergrensesnittets tilstand for avgang og vær samles som State-instanser
 * fra ViewModel, for automatisk å rekomponere visningen når disse tilstandene endrer seg.
 *
 * En LazyRow brukes for å vise listen over de neste 7 dagene øverst på skjermen. Hver dag
 * er representert med en ElevatedButton, og den valgte dagen er fremhevet med en mørkere farge.
 *
 * Værprognosen for den valgte dagen vises i en LazyColumn, med hvert element som representerer
 * værforholdene for en bestemt tid. Hvert element er representert av et com.example.thrillcast.ui.screens.mapScreen.TimeWeatherCard,
 * som viser detaljerte værforhold som værsymbol, temperatur, vindretning og vindhastighet.
 *
 * Når brukeren velger en annen dag, oppdateres den valgte dagen og værprognosen for den dagen automatisk
 * på grunn av tilstandsendringer i ViewModel.
 */
@Composable
fun FuturePage(weatherViewModel: WeatherViewModel, context : Context) {

    val takeoffUiState = weatherViewModel.takeoffUiState.collectAsState()
    val weatherUiState = weatherViewModel.forecastWeatherUiState.collectAsState()

    val buttonDays = listOf(
        1L, 2L, 3L, 4L, 5L, 6L, 7L
    ).map { LocalDate.now().plusDays(it) }
    var selectedButtonIndex by remember { mutableStateOf(0) }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        contentPadding = PaddingValues(6.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        itemsIndexed(buttonDays) { index, day ->
            val isSelected = index == selectedButtonIndex
            ElevatedButton(
                modifier = Modifier.padding(6.dp),
                onClick = {
                    selectedButtonIndex = index
                },
                shape = RoundedCornerShape(8.dp),
                colors = if (isSelected) ButtonDefaults.buttonColors(DarkerYellow) else ButtonDefaults.buttonColors(Yellow),

            ) {
                Text(
                    text = day.dayOfWeek.name.take(3),
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall.copy(
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                        fontSize = 15.sp
                    )
                )
            }
        }
    }
    LazyColumn(modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        weatherUiState.value.locationForecast?.let { forecastList ->
            items(forecastList.filter { it.time?.toLocalDate() == buttonDays[selectedButtonIndex] }) { it ->

                TimeWeatherCard(
                    context = context,
                    time = "${it.time?.hour ?: 99}:${it.time?.minute ?: 9}0",
                    greenStart = takeoffUiState.value.takeoff?.greenStart ?: 0,
                    greenStop = takeoffUiState.value.takeoff?.greenStop ?: 0,
                    symbolCode = it.data?.next_1_hours?.summary?.symbol_code
                        ?: it.data?.next_6_hours?.summary?.symbol_code
                        ?: "sleetshowersandthunder_polartwilight",
                    temperature = it.data?.instant?.details?.air_temperature ?: 0.0,
                    windDirection = it.data?.instant?.details?.wind_from_direction ?: 0.0,
                    windSpeed = it.data?.instant?.details?.wind_speed ?: 0.0
                )
            }
        }
    }
}