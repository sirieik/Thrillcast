package com.example.thrillcast.ui.screens.mapScreen
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thrillcast.R
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(viewModel: MapViewModel = viewModel()) {

    val norway = LatLng(62.0, 10.0)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(norway, 5.5f)
    }

    val uiState = viewModel.uiState.collectAsState()

    //Bruke denne til å legge inn lasteskjerm dersom kartet bruker tid
    var isMapLoaded by remember {mutableStateOf(false)}
    var bottomSheetVisible by remember { mutableStateOf(false)}
    var selectedMarker by remember { mutableStateOf<Marker?>(null) }

    var searchInput by remember { mutableStateOf("") }
    val hideKeyboard = LocalFocusManager.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { SearchBarDemo() },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                GoogleMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    onMapLoaded = {
                        //Her oppdaterer vi verdien til true dersom kartet er ferdig lastet inn
                        isMapLoaded = true

                    }
                ) {
                    uiState.value.takeoffs.forEach{ it ->
                        Marker(
                            state = MarkerState(it.value),
                            title = it.key,
                            icon = BitmapDescriptorFactory.fromResource(R.drawable.parachuting),
                            onInfoWindowClick = {
                                //Få inn hva som skjer når man trykker på infovindu m tekst
                                //Tenker at det er mer praktisk å få opp infoskjerm etter å trykke på
                                //"labelen" til markøren etter å trykke på den, i tilfelle man trykker på feil
                                //markør. I og med at det kommer til å være en del markører
                                selectedMarker = it
                                bottomSheetVisible = true
                            }
                        )
                    }
                }

                // Show the bottom sheet if a marker was selected
                selectedMarker?.let { marker ->
                    MapBottomInfoSheet(
                        selectedMarker = marker,
                        bottomSheetVisible = bottomSheetVisible,
                        onCloseSheet = {
                            selectedMarker = null
                        }
                    )
                }

                //Lasteskjerm om kartet ikke lastes inn
                if (!isMapLoaded) {
                    androidx.compose.animation.AnimatedVisibility(
                        modifier = Modifier,
                        visible = !isMapLoaded,
                        enter = EnterTransition.None,
                        exit = fadeOut()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                //.background(MaterialTheme.colors.background)
                                .wrapContentSize()
                        )
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBarDemo() {
    var searchInput by remember { mutableStateOf("") }
    val hideKeyboard = LocalFocusManager.current
    //Prover aa faa den exit knappen til å kun dukke opp dersom textfielden er trykket paa, men funker faen ikke
    //var textFieldClicked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF95B5F9)),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,

    ) {
        IconButton(
            onClick = {
            /*TODO - legge til NAV bar der man går tilbake til start skjerm*/
            }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Go back",
                tint = Color.Black
            )
        }
        OutlinedTextField(

            modifier = Modifier
                .border(2.dp, Color.LightGray, CircleShape)
                .width(320.dp)
                .height(60.dp)
                .clip(shape = CircleShape)
                .background(color = Color(0xFFF3EDF7)),
            value = searchInput,
            onValueChange = {
                searchInput = it},
            //onFocusEvent = {textFieldClicked = textFieldClicked.isFocused},
            placeholder = { Text("Find takeoff locations") },
            singleLine = true,
            maxLines = 1,
            shape = CircleShape,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "search",
                    tint = Color.Black
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        searchInput = ""
                        hideKeyboard.clearFocus()
                    }
                ) {

                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "close",
                        tint = Color.Black
                    )
                }
            }
        )

        /*IconButton(
            onClick = {
                /*TODO - legge til NAV bar der man går tilbake til start skjerm*/
            }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Go back",
                tint = Color.White
            )
        }*/
        /*
        //if(textFieldClicked) {
            IconButton(
                onClick = {
                    searchInput = ""
                    hideKeyboard.clearFocus()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Exit search"
                )
            }
        //}

         */
    }
}

@Preview
@Composable
fun MapScreenPreview() {
    MapScreen()
}

