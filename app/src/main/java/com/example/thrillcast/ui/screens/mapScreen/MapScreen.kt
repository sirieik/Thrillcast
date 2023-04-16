package com.example.thrillcast.ui.screens.mapScreen
import HolfuyWeatherViewModel
import Takeoff
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thrillcast.R
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    coroutineScope: CoroutineScope,
    modalSheetState : ModalBottomSheetState,
    mapViewModel: MapViewModel,
    holfuyWeatherViewModel: HolfuyWeatherViewModel,
    onNavigate: () -> Unit
) {

    val norway = LatLng(62.0, 10.0)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(norway, 5.5f)
    }

    val uiState = mapViewModel.uiState.collectAsState()

    //Bruke denne til å legge inn lasteskjerm dersom kartet bruker tid
    var isMapLoaded by remember {mutableStateOf(false)}
    var bottomSheetVisible by remember { mutableStateOf(false)}
    var selectedMarker by remember { mutableStateOf<Marker?>(null) }

    var selectedSearchItem by remember { mutableStateOf<Takeoff?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchBarDemo(
                onNavigate,
                mapViewModel,
                onTakeoffSelected = { takeoff ->
                    selectedSearchItem = takeoff
                    coroutineScope.launch {
                        if (modalSheetState.isVisible) {
                            modalSheetState.hide()
                        }
                        else {
                            holfuyWeatherViewModel.retrieveStationWeather(selectedSearchItem!!)
                            modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                        }
                    }
                }
            ) },
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
                    uiState.value.takeoffs.forEach{ takeoff ->
                        Marker(
                            state = MarkerState(takeoff.coordinates),
                            title = takeoff.name,
                            icon = BitmapDescriptorFactory.fromResource(R.drawable.parachuting),
                            onInfoWindowClick = {
                                //Få inn hva som skjer når man trykker på infovindu m tekst
                                //Tenker at det er mer praktisk å få opp infoskjerm etter å trykke på
                                //"labelen" til markøren etter å trykke på den, i tilfelle man trykker på feil
                                //markør. I og med at det kommer til å være en del markører
                                /*
                                selectedMarker = it
                                bottomSheetVisible = true
                                 */
                                coroutineScope.launch {
                                    if (modalSheetState.isVisible) {
                                        modalSheetState.hide()
                                    }
                                    else {
                                        holfuyWeatherViewModel.retrieveStationWeather(takeoff)
                                        holfuyWeatherViewModel.retrieveWindyWeather(takeoff)
                                        modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                    }
                                }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarDemo(onNavigate: () -> Unit, mapViewModel: MapViewModel, onTakeoffSelected: (Takeoff) -> Unit) {
    var searchInput by remember { mutableStateOf("") }
    val hideKeyboard = LocalFocusManager.current

    val uiState = mapViewModel.uiState.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF95B5F9)),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Column() {

            IconButton(
                onClick = onNavigate
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Go back",
                    tint = Color.Black
                )
            }
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                value = searchInput,
                onValueChange = { searchInput = it },
                placeholder = { Text("Find takeoff locations") },
                singleLine = true,
                maxLines = 1,
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
            if (searchInput.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    state = rememberLazyListState()
                ) {
                    items(uiState.value.takeoffs.filter {
                        it.name.contains(searchInput, ignoreCase = true)
                    }) { takeoff ->
                        ClickableText(
                            text = AnnotatedString(takeoff.name),
                            onClick = {
                                searchInput = ""
                                hideKeyboard.clearFocus()
                                onTakeoffSelected(takeoff)
                            },
                            modifier = Modifier.padding(16.dp),
                            style = TextStyle(fontSize = 20.sp)
                        )
                    }
                }
            }
        }
    }
}




