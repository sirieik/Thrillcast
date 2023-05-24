import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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

/*@Composable
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
    val windConditionColor = checkWindConditions(
        windDirection = windDirection, windSpeed = windSpeed,
        greenStart = greenStart, greenStop = greenStop
    ).color
    Card(
        modifier = Modifier
            .height(140.dp)
            .width(370.dp)
            .padding(3.dp)
            .border(8.dp, windConditionColor, shape = RoundedCornerShape(8.dp)),
        backgroundColor = Color(0xFF93B3F3),
        /*backgroundColor = checkWindConditions(
            windDirection = windDirection, windSpeed = windSpeed,
            greenStart = greenStart, greenStop = greenStop
        ).color,*/
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Image(
                    painter = painterResource(id = R.drawable.windarrow),
                    contentDescription = "wind direction",
                    modifier = Modifier.size(32.dp).rotate((windDirection + 90).toFloat())
                )
                Text(
                    text = "$windSpeed m/s",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Spacer(modifier = Modifier.height(17.dp))
                Text(
                    text = time,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Normal,
                )
                Text(
                    text = "$temperature°C",
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
                    modifier = Modifier
                        .size(64.dp),
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
}*/

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
    val windConditionColor = checkWindConditions(
    windDirection = windDirection, windSpeed = windSpeed,
    greenStart = greenStart, greenStop = greenStop
    ).color
    Card(
        modifier = Modifier
            .height(140.dp)
            .width(370.dp)
            .padding(3.dp),
        backgroundColor = Color(0xFF93B3F3),
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.width(15.dp))
            Column(
                modifier = Modifier
                    .weight(1.25f)
                    .padding(8.dp)
                    .fillMaxHeight()
                    .aspectRatio(1f)
                    .background(windConditionColor, shape = RoundedCornerShape(8.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,


            ){
                Image(
                    painter = painterResource(id = R.drawable.windarrow),
                    contentDescription = "wind direction",
                    modifier = Modifier
                        .size(32.dp)
                        .rotate((windDirection + 90).toFloat())
                )
                Text(
                    text = "$windSpeed m/s",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = time,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = "$temperature°C",
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
