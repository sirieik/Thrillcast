import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thrillcast.ui.theme.*
import com.example.thrillcast.ui.viewmodels.weather.WeatherViewModel

/**
 * @Composable funksjon som representerer InfoPage-skjermen i appen.
 *
 * Denne skjermen presenterer informasjon om den minste sertifiseringen som kreves,
 * koordinatene og høyde over havet (MOH) for takeoff-lokasjonen.
 *
 * @param weatherViewModel En ViewModel som gir takeoff-tilstanden for visningen.
 *
 * Brukergrensesnittets tilstand for takeoff samles som en State-instans
 * fra ViewModel.
 *
 * Informasjonen er presentert inne i et ElevatedCard, med hver bit av informasjon
 * vist som et separat Text-komponent. Hvert Text-komponent har fet skrift for tittelen,
 * og normal skrift for den faktiske informasjonen.
 *
 * Hvis det ikke er noen MOH-verdi tilgjengelig (dvs. MOH-verdien er 0), vil MOH-informasjonen
 * ikke bli vist.
 */

@Composable
fun InfoPage(weatherViewModel: WeatherViewModel) {
    val minCertificate = "PP2/SP2"
    val takeoffUiState = weatherViewModel.takeoffUiState.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        backgroundColor = Color(0xFF93B3F3),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Minimum certification:")
                    }
                    append(" $minCertificate")
                },
                fontSize = 18.sp,
                fontFamily = montserrat,
                fontWeight = FontWeight.Medium,
            )

            takeoffUiState.value.takeoff?.coordinates?.let { coordinates ->
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Coordinate:")
                        }
                        append(" ${coordinates.latitude}, ${coordinates.longitude}")
                    },
                    fontSize = 18.sp,
                    fontFamily = montserrat,
                    fontWeight = FontWeight.Medium,
                )
            }

            takeoffUiState.value.takeoff?.moh?.let { moh ->
                if (moh != 0) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("MOH:")
                            }
                            append(" $moh")
                        },
                        fontSize = 18.sp,
                        fontFamily = montserrat,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
        }
    }
}

