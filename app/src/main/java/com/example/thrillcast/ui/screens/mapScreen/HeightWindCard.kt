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
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thrillcast.R
import com.example.thrillcast.data.repositories.WindyWinds
import com.example.thrillcast.ui.theme.DarkerYellow
import com.example.thrillcast.ui.theme.Yellow
import java.util.*
import kotlin.math.roundToInt
import androidx.compose.material.Card

/**
 * Composable som viser vinddata for forskjellige høyder.
 *
 * @param heightList En liste over strenger som representerer forskjellige høyder.
 * @param windyWindsList En liste over WindyWinds-objekter som representerer vinddata på forskjellige tidspunkter.
 *
 * Denne komponenten lar brukeren velge en tid, representert av knapper. Den valgte knappen vil
 * endre farge for å indikere at den er valgt. Vinddata for det valgte tidspunktet brukes deretter til
 * å vise vindretning og hastighet for en bestemt høyde.
 *
 * Høyden kan velges ved hjelp av en vertikal Slider. Vinddata for den valgte høyden
 * vises på skjermen. Vindretningen vises som et bilde av en pil som roterer
 * basert på vindretningsdata. Vindhastigheten vises som tekst under vindretningsbildet.

 */

/*@Composable
fun HeightWindCard(heightList: List<String>, windyWindsList: List<WindyWinds>){

    var selectedHeightIndex by remember { mutableStateOf(0) }
    var selectedButtonIndex by remember { mutableStateOf(0) }

    // Henter vindretning og -hastighet for valgt høyde og klokkeslett.
    val windDirection = windyWindsList.getOrNull(selectedButtonIndex)
        ?.getWindDirectionForHeight(selectedHeightIndex)
    val windSpeed = windyWindsList.getOrNull(selectedButtonIndex)
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
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = "Height wind",
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
            LazyRow(
                modifier = Modifier,
                contentPadding = PaddingValues(6.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top
            ) {
                itemsIndexed(windyWindsList) { index, it ->
                    val isSelected = index == selectedButtonIndex
                    ElevatedButton(
                        modifier = Modifier.padding(6.dp),
                        onClick = {
                            selectedButtonIndex = index
                        },
                        colors = if (isSelected) ButtonDefaults.buttonColors(DarkerYellow) else ButtonDefaults.buttonColors(Yellow),
                        shape = RoundedCornerShape(10.dp),
                    ) {
                        Text(
                            text = "${Date(it.time).hours}:00",
                            style = androidx.compose.material3.MaterialTheme.typography.bodySmall.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                                fontSize = 15.sp
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
                    Text(text = stringResource(id = R.string.height), style = androidx.compose.material3.MaterialTheme.typography.bodyMedium, fontSize = 25.sp, fontWeight = FontWeight.Normal)
                    IconButton(
                        modifier = Modifier.padding(6.dp),
                        onClick = {
                            if (selectedHeightIndex < heightList.size - 1) {
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
                        valueRange = 0f..(heightList.size - 1).toFloat(),
                        steps = heightList.size - 2
                    )
                    IconButton(
                        modifier = Modifier.padding(6.dp),
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
                    Text(text = "${heightList[selectedHeightIndex]} moh", style = androidx.compose.material3.MaterialTheme.typography.bodySmall)
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
                                .fillMaxWidth()
                                .rotate((windDirection + 90.0).toFloat())
                        )
                    }
                    Text(
                        text = "${roundDouble(windSpeed ?: 0.0)} m/s",
                        style = androidx.compose.material3.MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}*/

@Composable
fun HeightWindCard(heightList: List<String>, windyWindsList: List<WindyWinds>) {
    var selectedHeightIndex by remember { mutableStateOf(0) }
    var selectedButtonIndex by remember { mutableStateOf(0) }

    // Henter vindretning og -hastighet for valgt høyde og klokkeslett.
    val windDirection = windyWindsList.getOrNull(selectedButtonIndex)
        ?.getWindDirectionForHeight(selectedHeightIndex)
    val windSpeed = windyWindsList.getOrNull(selectedButtonIndex)
        ?.getWindSpeedForHeight(selectedHeightIndex)

    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxSize()
            .padding(6.dp),
        backgroundColor = Color(0xFF93B3F3), // Sett ønsket bakgrunnsfarge her
        shape = RoundedCornerShape(8.dp), // Sett ønsket hjørneradius her
        elevation = 4.dp // Sett ønsket heving her
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = "Height wind",
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )
            //Spacer(modifier = Modifier.height(16.dp))
            LazyRow(
                //modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(6.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top
            ) {
                itemsIndexed(windyWindsList){ index, it ->
                    val isSelected = index == selectedButtonIndex
                    ElevatedButton(
                        modifier = Modifier.padding(6.dp),
                        onClick = {
                            selectedButtonIndex = index
                        },
                        colors = if (isSelected) ButtonDefaults.buttonColors(DarkerYellow) else ButtonDefaults.buttonColors(Yellow),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Text(
                            text = "${Date(it.time).hours}:00",
                            style = androidx.compose.material3.MaterialTheme.typography.bodySmall.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                                fontSize = 15.sp
                            )
                        )
                    }
                }
            }
            //Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.6f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(0.3f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.height),
                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                        fontSize = 25.sp, fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    IconButton(
                        onClick = {
                            if (selectedHeightIndex < heightList.size - 1) {
                                selectedHeightIndex++
                            }
                        },
                        modifier = Modifier.size(30.dp)
                    ) {
                        Icon(
                            //imageVector = Icons.Default.Add,
                            painterResource(id = R.drawable.add),
                            contentDescription = "Increase height",
                            modifier = Modifier.size(30.dp)
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
                        valueRange = 0f..(heightList.size - 1).toFloat(),
                        steps = heightList.size - 2
                    )
                    IconButton(
                        onClick = {
                            if (selectedHeightIndex > 0) {
                                selectedHeightIndex--
                            }
                        },
                        //modifier = Modifier.padding(6.dp)
                    ) {
                        Icon(
                            //painterResource(id = R.drawable.minus_sign),
                            painterResource(id = R.drawable.minus),
                            contentDescription = "Decrease height",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "${heightList[selectedHeightIndex]} moh",
                        style = androidx.compose.material3.MaterialTheme.typography.bodySmall
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(0.7f)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (windDirection != null) {
                        Image(
                            painter = painterResource(id = R.drawable.windarrow),
                            contentDescription = "wind direction",
                            modifier = Modifier
                                .fillMaxWidth()
                                .rotate((windDirection + 90.0).toFloat())
                        )
                    }
                    Text(
                        text = "${roundDouble(windSpeed ?: 0.0)} m/s",
                        style = androidx.compose.material3.MaterialTheme.typography.bodySmall
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