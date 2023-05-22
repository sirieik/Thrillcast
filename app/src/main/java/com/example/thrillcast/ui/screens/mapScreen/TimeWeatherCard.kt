import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thrillcast.R
import com.example.thrillcast.ui.common.calculations.checkWindConditions

/**
 * Composable funksjon som viser informasjon om været for et gitt tidspunkt i et card layout.
 * Card-et viser også om vindforholdene er gode for paragliding ved å vise enten grønn(bra),
 * gul(ok) eller rød(dårlig).
 *
 * @param context Konteksten som brukes til å hente ressurser.
 * @param time Strengen som viser tidspunktet på kortet.
 * @param greenStart Startverdien for den grønne sonen i vindretningssirkelen.
 * @param greenStop Sluttverdien for den grønne sonen i vindretningssirkelen.
 * @param symbolCode Symbolkoden som brukes til å hente tilhørende drawable-ressurs.
 * @param temperature Temperaturen som vises på kortet.
 * @param windDirection Vindretningen i grader.
 * @param windSpeed Vindhastigheten.
 */
@Composable
fun TimeWeatherCard(
    context: Context,
    time: String,
    greenStart: Int,
    greenStop: Int,
    symbolCode: String,
    temperature: Double,
    windDirection: Double,
    windSpeed: Double,
) {
    ElevatedCard(
        modifier = Modifier
            .height(125.dp)
            .width(350.dp)
            .clip(RoundedCornerShape(4.dp))
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Card(
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(1f)
                    .padding(6.dp),
                backgroundColor = checkWindConditions(
                    windDirection = windDirection, windSpeed = windSpeed,
                    greenStart = greenStart, greenStop = greenStop
                ).color
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.windarrow),
                        contentDescription = "wind direction",
                        modifier = Modifier
                            .size(32.dp)
                            .rotate((windDirection + 90).toFloat())
                    )
                    Text(
                        text = "$windSpeed m/s",
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = time,
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = "$temperature°C",
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Image(
                modifier = Modifier.weight(1f),
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

