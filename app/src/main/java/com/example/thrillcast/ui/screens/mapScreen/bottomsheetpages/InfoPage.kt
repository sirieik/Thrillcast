import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thrillcast.ui.viewmodels.weather.WeatherViewModel

@Composable
fun InfoPage(weatherViewModel: WeatherViewModel) {

    val minCertificate = "PP2/SP2"
    val takeoffUiState = weatherViewModel.takeoffUiState.collectAsState()
    ElevatedCard(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Minimum certification: ")
                }
                append("$minCertificate")
            },
            fontSize = 22.sp,
            modifier = Modifier.padding(8.dp)
        )
        takeoffUiState.value.takeoff?.coordinates?.let {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Coordinate: ")
                    }
                    append("${it.latitude}, ${it.longitude}")
                },
                fontSize = 22.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
        takeoffUiState.value.takeoff?.moh?.let {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("MOH: ")
                    }
                    append("$it")
                },
                fontSize = 22.sp,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}