package com.example.thrillcast.ui.screens.mapScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
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
import com.example.thrillcast.ui.theme.Yellow
import com.example.thrillcast.ui.viewmodels.weather.WeatherViewModel
import java.util.*
import kotlin.math.roundToInt

/**
 * En sammensatt komponent som viser høydevindinformasjon.
 *
 * @param weatherViewModel ViewModel som inneholder høydevind-data.
 */


//send inn uistates?
@Composable
fun HeightWindCard(weatherViewModel: WeatherViewModel){

    // Henter heightWindUIstate, som inneholder data vi trenger for høydevind.
    val heightWindUiState = weatherViewModel.heightWindUiState.collectAsState()

    // Henter listen over høydevinddata, eller en tom liste hvis den er null.
    val windDataList = heightWindUiState.value.windyWindsList?.take(7) ?: emptyList()

    // Liste med høyder som skal vises.
    val heights = listOf("600", "900", "1500", "2000")

    var selectedHeightIndex by remember { mutableStateOf(0) }
    var selectedButtonIndex by remember { mutableStateOf(0) }

    // Henter vindretning og -hastighet for valgt høyde og klokkeslett.
    val windDirection = windDataList.getOrNull(selectedButtonIndex)
        ?.getWindDirectionForHeight(selectedHeightIndex)
    val windSpeed = windDataList.getOrNull(selectedButtonIndex)
        ?.getWindSpeedForHeight(selectedHeightIndex)

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
                text = "Height wind",
                fontSize = 20.sp
            )
            LazyRow(
                modifier = Modifier,
                contentPadding = PaddingValues(6.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top
            ) {
                itemsIndexed(windDataList) { index, it ->
                    val isSelected = index == selectedButtonIndex
                    ElevatedButton(
                        modifier = Modifier.padding(6.dp),
                        onClick = {
                            selectedButtonIndex = index
                        },
                        colors = ButtonDefaults.buttonColors(Yellow),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "${Date(it.time).hours}:00",
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
                    Text(text = stringResource(id = R.string.height))
                    IconButton(
                        onClick = {
                            if (selectedHeightIndex < heights.size - 1) {
                                selectedHeightIndex ++
                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Increase height"
                        )
                    }
                    Slider(

                        modifier = Modifier
                            .rotate(degrees = -90f)
                            .padding(20.dp),

                        value = selectedHeightIndex.toFloat(),
                        onValueChange = {
                            selectedHeightIndex = it.toInt()
                        },
                        onValueChangeFinished = {

                        },
                        valueRange = 0f..(heights.size - 1).toFloat(),
                        steps = heights.size - 2
                    )
                    IconButton(
                        onClick = {
                            if (selectedHeightIndex > 0) {
                                selectedHeightIndex --
                            }
                        }
                    ) {
                        Icon(
                            painterResource(id = R.drawable.minus_sign),
                            contentDescription = "Decrease height"
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "${heights[selectedHeightIndex]} moh")
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
                        text = "${roundDouble(windSpeed ?: 0.0)} m/s"
                    )
                }
            }
        }
    }
}

/**
 * Runder av et desimaltall til to desimaler.
 *
 * @param number Tallet som skal rundes av.
 * @return Det avrundede tallet med to desimaler.
 */
fun roundDouble(number: Double): Double = ((number * 100.0).roundToInt() / 100.0)