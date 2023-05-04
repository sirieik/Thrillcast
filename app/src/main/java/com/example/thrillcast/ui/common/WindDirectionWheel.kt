
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.thrillcast.ui.theme.FlightGreen
import com.example.thrillcast.ui.theme.Red60
import kotlin.math.cos
import kotlin.math.sin

/*
TO-DO:
Add the yellow zones as well, but i think it might be difficult to do
 */

@Composable
fun WindDirectionWheel(
    greenStart: Int, greenStop: Int,
    //yellowStart: Int, yellowStop: Int,
    windDirection: Int,
    modifier: Modifier = Modifier
) {

    //Since 0 represents 3 o' clock, we have to withdraw 90 degrees
    val greenStartAngle = (greenStart - 90).toFloat()
    val greenStopAngle = (greenStop - 90).toFloat()
    //val yellowStartAngle = (yellowStart - 90).toFloat()
    //val yellowStopAngle = (yellowStop - 90).toFloat()

    //CHAD
    // Convert wind direction to radians
    val windDirectionRadians = Math.toRadians((windDirection - 90).toDouble())
    //CHAD


    Canvas(
        modifier = Modifier
            .size(100.dp)
            .aspectRatio(1f)
            .padding(20.dp)
            .border(1.dp, Color.Black, CircleShape),
        onDraw = {

            //CHAD
            val centerX = size.width / 2
            val centerY = size.height / 2

            // Calculate vector pointing towards the center
            val vectorX = cos(windDirectionRadians).toFloat()
            val vectorY = sin(windDirectionRadians).toFloat()

            // Calculate end point on the edge of the circle
            val radius = size.width / 2
            val endX = centerX + radius * vectorX
            val endY = centerY + radius * vectorY
            //CHAD


            val sweepAngle = greenStopAngle - greenStartAngle
            if (sweepAngle <= 0) {
                drawCircle(FlightGreen)
                drawArc(
                    color = Color.LightGray,
                    startAngle = greenStartAngle,
                    //We have to do the area that isnt flyable to display, because otherwise it doesn't work
                    sweepAngle = sweepAngle,
                    useCenter = true
                )
            } else {
                drawCircle(Color.LightGray)
                drawArc(
                    color = FlightGreen,
                    startAngle = greenStartAngle,
                    //We have to do the area that isnt flyable to display, because otherwise it doesn't work
                    sweepAngle = sweepAngle,
                    useCenter = true
                )
            }

            //Sirkelen i bunn er grønn og skal representere "bra-sonen"
            //over legger jeg en gul sirkel over de områdene som ikke er bra
            //jeg prøver under å legge en hvit sirkel over stedene som er no-go
            //Det funker ikke

            //Don't understand why this doesn't work:

/*
            drawCircle(Color.com.example.thrillcast.data.holfuy.stations.Green)
            drawArc(
                color = Color.White,
                startAngle = yellowStartAngle,
                //We have to do the area that isnt flyable to display, because otherwise it doesn't work
                sweepAngle = yellowStopAngle - yellowStartAngle,
                useCenter = true
            )

 */



            //CHAD
            // Draw line for wind direction
            drawLine(
                color = Red60,
                start = Offset(endX, endY),
                end = Offset(centerX, centerY),
                strokeWidth = 5.dp.toPx()
            )
            //CHAD

        }
    )
}

@Preview
@Composable
fun WheelPreview() {
    WindDirectionWheel(180, 190, 90)
}