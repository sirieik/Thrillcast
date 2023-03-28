import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.thrillcast.R

@Composable
fun InfoCard(modifier: Modifier = Modifier) {
    // uistate?
    Box {
        val popupWidth = modifier.fillMaxWidth()
        val popupHeight = 200.dp

        // NB : knytte denne til marker på kartet - hvordan
        var popupControl by remember { mutableStateOf (false) }
        Button (onClick = { popupControl = true }) {
            Text("Dette er en lokasjon på kartet")
        }

        if (popupControl){
            Popup(
                alignment = Alignment.CenterStart,
                onDismissRequest = { popupControl = false }
            ) {
                Card(
                    // NB : modifier = modifier.padding(8.dp).size(popupWidth, popupHeight),
                    shape = RoundedCornerShape(8.dp)
                    //elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    // NB : blir det stygt med elevation
                ) {
                    Column ( modifier = Modifier
                        .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row (modifier = modifier.fillMaxWidth().height(20.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            /* three buttons */
                            Button(
                                onClick = {},
                                contentPadding = PaddingValues(
                                    start = 20.dp,
                                    top = 12.dp,
                                    end = 20.dp,
                                    bottom = 12.dp
                                )
                            ){
                                /* content: an icon */
                                Image(
                                    modifier = modifier
                                        .size(64.dp)
                                        .padding(8.dp)
                                        .clip(RoundedCornerShape(50)),
                                    contentScale = ContentScale.Crop,
                                    // contentscale så bildene ikke kuttes av
                                    painter = painterResource(R.drawable.baseline_info_24),
                                    /*
                                     * Content Description is not needed here - image is decorative, and setting a null content
                                     * description allows accessibility services to skip this element during navigation.
                                     */
                                    contentDescription = null
                                    // NB : burde ha med dette for å sikre UU
                                )
                            }
                            Button(
                                onClick = {},
                                contentPadding = PaddingValues(
                                    start = 20.dp,
                                    top = 12.dp,
                                    end = 20.dp,
                                    bottom = 12.dp
                                )
                            ){
                                Text(
                                    text = "NOW"
                                )
                            }
                            Button(
                                onClick = {},
                                contentPadding = PaddingValues(
                                    start = 20.dp,
                                    top = 12.dp,
                                    end = 20.dp,
                                    bottom = 12.dp
                                )
                            ){
                                Text(
                                    text = "TOMORROW"
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}