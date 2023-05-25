package com.example.thrillcast.ui.common

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thrillcast.R

/**
 * @Composable funksjon som viser informasjon om gjeldende vær i et card layout.
 *
 * @param windDirection Vindretningen i grader.
 * @param speed Vindhastigheten.
 * @param unit Enheten for vindhastighet (f.eks. "m/s").
 * @param gust Vindkasthastigheten.
 * @param symbolCode Symbolkoden for værforhold.
 * @param temperature Gjeldende temperatur.
 * @param greenStart Startverdien for den grønne sonen i vindretningssirkelen.
 * @param greenStop Sluttverdien for den grønne sonen i vindretningssirkelen.
 * @param context Context som brukes til å hente ressurser.
 */

@SuppressLint("DiscouragedApi")
@Composable
fun NowWeatherCard(
    windDirection: Int,
    speed: Double,
    unit: String,
    gust: Double,
    symbolCode: String,
    temperature: Double,
    greenStart: Int,
    greenStop: Int,
    context: Context,
    onClick: (() -> Unit)? = null //Denne skal være for å navigere til valgt lokasjon fra favorittskjermen
) {
    val cardModifier = if (onClick != null) {
        Modifier.clickable { onClick() }
    } else {
        Modifier
    }

    Card(
        modifier = Modifier
            .height(140.dp)
            .width(370.dp)
            .padding(3.dp)
            .then(cardModifier),
        backgroundColor = Color(0xFF93B3F3), // Sett ønsket bakgrunnsfarge her
        elevation = 4.dp, // Sett ønsket heving her
        shape = RoundedCornerShape(8.dp) // Sett ønsket hjørneradius her
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                //modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                WindDirectionWheel(
                    greenStart = greenStart,
                    greenStop = greenStop,
                    windDirection = windDirection
                )
                Text(
                    text = "$speed($gust) $unit",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.now),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1
                )
                Text(
                    text = "$temperature °C",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 30.sp,
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(64.dp),
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
