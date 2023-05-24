package com.example.thrillcast.ui.common
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.thrillcast.ui.theme.FlightGreen
import com.example.thrillcast.ui.theme.Red60
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

/**
 * @Composable funksjon som tegner en sirkulær vindretningsindikator med et uthevet grønt område.
 * Det grønne området viser hvilken retning man ønsker vinden fra for å kunne lette fra bakken.
 *
 * @param greenStart Startvinkelen (i grader) for den uthevede (grønne) sektoren på hjulet, med 0 på toppen og går med klokken.
 * @param greenStop Sluttvinkelen (i grader) for den uthevede (grønne) sektoren på hjulet, med 0 på toppen og går med klokken.
 * @param windDirection Vindretningen (i grader), med 0 på toppen og går med klokken. Vindretningen er representert som en linje.
 *
 * Denne funksjonen lager et sirkulært vindretninghjul, med en spesifikk sektor uthevet i grønt.
 * Resten av sirkelen er i lys grå. Vindretningen er indikert med en rød linje.
 * Sirkelen har en sort kantlinje, og vindretninglinjen er rød. Den uthevede (grønne) sektoren
 * starter ved 'greenStart' grader og stopper ved 'greenStop' grader, med vinkler tatt med klokken fra toppen.
 *
 */
@Composable
fun WindDirectionWheel(
    greenStart: Int,
    greenStop: Int,
    windDirection: Int,
) {

    //Disse er konstanter for en gitt state av denne Composablen.
    val greenStartAngle = (greenStart - 90).toFloat()
    val greenStopAngle = (greenStop - 90).toFloat()
    val windDirectionRadians = Math.toRadians((windDirection - 90).toDouble())
    val vectorX = cos(windDirectionRadians).toFloat()
    val vectorY = sin(windDirectionRadians).toFloat()
    val sweepAngle = greenStopAngle - greenStartAngle
    val colors = if (sweepAngle <= 0) listOf(FlightGreen, Color.LightGray) else listOf(Color.LightGray, FlightGreen)

    Canvas(
        modifier = Modifier
            .size(100.dp)
            .aspectRatio(1f)
            .padding(20.dp)
            .border(1.dp, Color.Black, CircleShape),
        onDraw = {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val radius = size.width / 2
            val endX = centerX + radius * vectorX
            val endY = centerY + radius * vectorY

            drawCircle(colors[0])
            drawArc(
                color = colors[1],
                startAngle = greenStartAngle,
                sweepAngle = abs(sweepAngle), // sweepAngle bør alltid være positiv
                useCenter = true
            )
            drawLine(
                color = Red60,
                start = Offset(endX, endY),
                end = Offset(centerX, centerY),
                strokeWidth = 5.dp.toPx()
            )
        }
    )
}