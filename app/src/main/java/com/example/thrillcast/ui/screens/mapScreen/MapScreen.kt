package com.example.thrillcast.ui.screens.mapScreen
import Takeoff
import WeatherViewModel
import androidx.compose.animation.Crossfade
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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

import androidx.compose.ui.draw.clip

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
import java.util.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    coroutineScope: CoroutineScope,
    modalSheetState : ModalBottomSheetState,
    mapViewModel: MapViewModel,
    weatherViewModel: WeatherViewModel,
    searchBarViewModel: SearchBarViewModel,
    onNavigate: () -> Unit
) {

    val norway = LatLng(62.0, 10.0)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(norway, 5.5f)
    }
    val state = searchBarViewModel.state
    val takeoffsUiState = mapViewModel.takeoffsUiState.collectAsState()

    //Bruke denne til å legge inn lasteskjerm dersom kartet bruker tid
    var isMapLoaded by remember {mutableStateOf(false)}
    var bottomSheetVisible by remember { mutableStateOf(false)}
    var selectedMarker by remember { mutableStateOf<Marker?>(null) }

    var selectedSearchItem by remember { mutableStateOf<Takeoff?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),

        
        topBar = {

            Crossfade(
                targetState = state.isSearchBarVisible,
                animationSpec = tween(500)
            ) {
                if (it) {
                    SearchBar(
                        onCloseIconClick = {
                            searchBarViewModel.onAction(UserAction.CloseActionClicked)
                        },
                        onNavigate,
                        mapViewModel,
                        onTakeoffSelected = { takeoff ->
                            selectedSearchItem = takeoff
                            coroutineScope.launch {
                                if (modalSheetState.isVisible) {
                                    modalSheetState.hide()
                                } else {
                                    weatherViewModel.retrieveStationWeather(selectedSearchItem!!)
                                    modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                }
                            }
                        }
                    )
                } else {
                    TopBar(
                        onSearchIconClick = {
                            searchBarViewModel.onAction(UserAction.SearchIconClicked)
                        },
                        onNavigate,
                        mapViewModel,
                        onTakeoffSelected = { takeoff ->
                            selectedSearchItem = takeoff
                            coroutineScope.launch {
                                if (modalSheetState.isVisible) {
                                    modalSheetState.hide()
                                } else {

                                    weatherViewModel.retrieveStationWeather(selectedSearchItem!!)
                                    modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                }
                            }
                        }
                    )
                }
            }
        },
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
                    takeoffsUiState.value.takeoffs.forEach{ takeoff ->
                         Marker(
                            state = MarkerState(takeoff.coordinates),
                            title = takeoff.name,
                            icon = BitmapDescriptorFactory.fromResource(R.drawable.parachuting),
                            onInfoWindowClick = {
                                //Få inn hva som skjer når man trykker på infovindu m tekst
                                //Tenker at det er mer praktisk å få opp infoskjerm etter å trykke på
                                //"labelen" til markøren etter å trykke på den, i tilfelle man trykker på feil
                                //markør. I og med at det kommer til å være en del markører
                                cameraPositionState.position = CameraPosition.Builder()
                                    .target(takeoff.coordinates)
                                    .zoom(9f)
                                    .build()
                                //selectedMarker = it
                                coroutineScope.launch {
                                    if (modalSheetState.isVisible) {
                                        modalSheetState.hide()
                                    }
                                    else {

                                        weatherViewModel.retrieveStationWeather(takeoff)
                                        modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                    }
                                }
                            }
                        )
                    }
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

@Composable
fun TopBar(
    onSearchIconClick: () -> Unit,
    onNavigate: () -> Unit,
    mapViewModel: MapViewModel,
    onTakeoffSelected: (Takeoff) -> Unit
) {

    var searchInput by remember { mutableStateOf("") }
    val hideKeyboard = LocalFocusManager.current

    val uiState = mapViewModel.takeoffsUiState.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF355e3b))
            .height(60.dp)
            //.clip(RoundedCornerShape(40))
            .border(1.dp, color = Color(0xFF90ee90), RoundedCornerShape(2)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = onNavigate
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back Icon",
                tint = Color(0xFF90ee90)
            )
        }

        Text(
            text = "Paragliding", style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF90ee90),
            fontSize = 30.sp,
        )

        IconButton(
            onClick = onSearchIconClick
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon",
                tint = Color(0xFF90ee90)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    onCloseIconClick: () -> Unit,
    onNavigate: () -> Unit,
    mapViewModel: MapViewModel,
    onTakeoffSelected: (Takeoff) -> Unit
) {
    var searchInput by remember { mutableStateOf("") }
    val hideKeyboard = LocalFocusManager.current

    val uiState = mapViewModel.takeoffsUiState.collectAsState()

    Column() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF001A40)),
            //.border(1.dp, color = Color(0xFFD7E2FF), RoundedCornerShape(2)),
            //horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,

            ) {

            OutlinedTextField(
                modifier = Modifier
                    //.width(320.dp)
                    .fillMaxWidth()
                    .height(60.dp)
                    .clip(shape = RoundedCornerShape(15))
                    .background(Color.Transparent),
                value = searchInput,
                onValueChange = { searchInput = it },
                placeholder = { Text("Find takeoff locations", color = Color(0xFFD7E2FF)) },
                singleLine = true,
                maxLines = 1,
                shape = RoundedCornerShape(15),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search Icon",
                        tint = Color(0xFFD7E2FF)
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if (searchInput.isNotEmpty()) {
                                searchInput = ""
                            } else {
                                onCloseIconClick()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close Icon",
                            tint = Color(0xFFD7E2FF)
                        )
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color(0xFFD7E2FF),
                    //unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color(0xFFD7E2FF),
                    cursorColor = Color(0xFFD7E2FF),
                    unfocusedTrailingIconColor = Color.White,
                    focusedTrailingIconColor = Color.Black
                )
            )
        }

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            if (searchInput.isNotEmpty()) {
                items(uiState.value.takeoffs.filter {
                    it.name.contains(searchInput, ignoreCase = true)
                }) { takeoff ->
                    Card {
                        ClickableText(
                            text = AnnotatedString(takeoff.name),
                            onClick = {
                                searchInput = ""
                                hideKeyboard.clearFocus()
                                onTakeoffSelected(takeoff)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFF001A40))
                                .padding(vertical = 16.dp),
                            style = TextStyle(fontSize = 20.sp, color = Color(0xFFD7E2FF))
                        )
                    }
                }
            }
        }
    }
}
