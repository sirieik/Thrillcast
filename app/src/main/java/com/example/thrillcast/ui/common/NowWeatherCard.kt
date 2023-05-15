import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.thrillcast.R
import com.example.thrillcast.data.datamodels.Wind
import com.example.thrillcast.ui.viewmodels.map.Takeoff

/**
 * Composable funksjon som viser informasjon om gjeldende vær i et card layout.
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

    ElevatedCard(
        modifier = Modifier
            .height(125.dp)
            .width(350.dp)
            .then(cardModifier)
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                WindDirectionWheel(
                    greenStart = greenStart,
                    greenStop = greenStop,
                    windDirection = windDirection,
                )
                Text(
                    text = "$speed($gust) $unit",
                    style = MaterialTheme.typography.bodyMedium
                )

            }
            Column(
                modifier = Modifier.weight(0.33f, true),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(
                    text = stringResource(id = R.string.now),
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1
                )
                Text(
                    text = "$temperature °C",
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1
                )
            }
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