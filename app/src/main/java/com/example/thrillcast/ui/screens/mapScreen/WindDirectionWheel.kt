
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun WindDirectionWheel(greenStart: Int, greenStop: Int, windDirection: Int) {

    //Since 0 represents 3 o' clock, we have to withdraw 90 degrees
    val greenStartAngle = (greenStart - 90).toFloat()
    val greenStopAngle = (greenStop - 90).toFloat()

    //CHAD
    // Convert wind direction to radians
    val windDirectionRadians = Math.toRadians((windDirection - 90).toDouble())
    //CHAD


    Canvas(
        modifier = Modifier
            .size(200.dp)
            .aspectRatio(1f)
            .padding(16.dp),
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

            drawCircle(Color.Green)
            drawArc(
                color = Color.White,
                startAngle = greenStartAngle,
                //We have to do the area that isnt flyable to display, because otherwise it doesn't work
                sweepAngle = greenStopAngle - greenStartAngle,
                useCenter = true
            )

            //CHAD
            // Draw line for wind direction
            drawLine(
                color = Color.Red,
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
    WindDirectionWheel(240, 0, 90)
}