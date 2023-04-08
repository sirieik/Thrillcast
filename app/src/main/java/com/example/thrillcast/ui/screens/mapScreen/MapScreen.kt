package com.example.thrillcast.ui.screens.mapScreen
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
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
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {
            /*TODO - legge til NAV bar der man går tilbake til start skjerm*/
            }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Go back"
            )
        }
        OutlinedTextField(
            value = searchInput,
            onValueChange = { searchInput = it },
            //onFocusEvent = {textFieldClicked = textFieldClicked.isFocused},
            label = { Text("Find takeoff locations") },
            modifier = Modifier.weight(1f)
        )
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
    }
}

@Preview
@Composable
fun MapScreenPreview() {
    MapScreen()
}

