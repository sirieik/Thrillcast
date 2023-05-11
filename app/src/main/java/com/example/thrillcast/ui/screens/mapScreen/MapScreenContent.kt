package com.example.thrillcast.ui.screens.mapScreen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.animation.Crossfade
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import checkWindConditions
import com.example.thrillcast.R
import com.example.thrillcast.data.datamodels.Wind
import com.example.thrillcast.ui.common.WindCondition
import com.example.thrillcast.ui.theme.DarkBlue
import com.example.thrillcast.ui.theme.Silver
import com.example.thrillcast.ui.viewmodels.map.MapViewModel
import com.example.thrillcast.ui.viewmodels.map.Takeoff
import com.example.thrillcast.ui.viewmodels.weather.WeatherViewModel
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import isDegreeBetween
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MapScreenContent(
    coroutineScope: CoroutineScope,
    modalSheetState : ModalBottomSheetState,
    mapViewModel: MapViewModel,
    weatherViewModel: WeatherViewModel,
    searchBarViewModel: SearchBarViewModel,
    context: Context,
    onNavigate: () -> Unit
) {

    val norway = LatLng(62.0, 10.0)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(norway, 5.5f)
    }
    val state = searchBarViewModel.state
    val takeoffsUiState = mapViewModel.takeoffsUiState.collectAsState()

    var selectedSearchItem by remember { mutableStateOf<Takeoff?>(null) }

    weatherViewModel.retrieveLocationsWind(takeoffsUiState.value.takeoffs)

    val locationsWindUiState = weatherViewModel.locationsWindUiState.collectAsState()

    val locationsAndWindMap = takeoffsUiState.value.takeoffs.zip(locationsWindUiState.value.windList)

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
                            cameraPositionState.position = CameraPosition.Builder()
                                .target(selectedSearchItem!!.coordinates)
                                .zoom(9f)
                                .build()
                            coroutineScope.launch {
                                if (modalSheetState.isVisible) {
                                    modalSheetState.hide()
                                } else {
                                    weatherViewModel.updateChosenTakeoff(selectedSearchItem!!)
                                    //weatherViewModel.retrieveStationWeather(takeoff)
                                    weatherViewModel.retrieveHeightWind(selectedSearchItem!!)
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

                                    weatherViewModel.updateChosenTakeoff(selectedSearchItem!!)
                                    //weatherViewModel.retrieveStationWeather(takeoff)
                                    weatherViewModel.retrieveHeightWind(selectedSearchItem!!)
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
                    properties = MapProperties(mapType = MapType.TERRAIN)
                ) {
                    locationsAndWindMap.forEach{

                         val takeoff = it.first
                         val wind = it.second

                         Marker(
                            state = MarkerState(takeoff.coordinates),
                            title = takeoff.name,
                            icon = defaultMarker(markerColor(wind, takeoff)),
                            onInfoWindowClick = {
                                cameraPositionState.position = CameraPosition.Builder()
                                    .target(takeoff.coordinates)
                                    .zoom(9f)
                                    .build()
                                coroutineScope.launch {
                                    if (modalSheetState.isVisible) {
                                        modalSheetState.hide()
                                    }
                                    else {
                                        weatherViewModel.updateChosenTakeoff(takeoff)
                                        weatherViewModel.retrieveHeightWind(takeoff = takeoff)
                                        modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                    }
                                }
                            },
                             //Bottomsheet kommer med en gang man trykker
                            onClick =  {

                                cameraPositionState.position = CameraPosition.Builder()
                                    .target(takeoff.coordinates)
                                    .zoom(9f)
                                    .build()

                                coroutineScope.launch {
                                    if (modalSheetState.isVisible) {
                                        modalSheetState.hide()
                                    }
                                    else {
                                        weatherViewModel.updateChosenTakeoff(takeoff)
                                        //weatherViewModel.retrieveStationWeather(takeoff)
                                        weatherViewModel.retrieveHeightWind(takeoff = takeoff)
                                        modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
                                    }
                                }
                                true
                            }
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
            .background(DarkBlue)
            .height(60.dp)
            .border(1.dp, color = Silver, RectangleShape),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = onNavigate
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Favorite Icon",
                tint = Silver
            )
        }

        Text(
            text = "Paragliding", style = MaterialTheme.typography.titleSmall,
            color = Silver,
            fontSize = 30.sp,
        )

        IconButton(
            onClick = onSearchIconClick
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon",
                tint = Silver
            )
        }
    }
}

fun MarkerIcon(wind: Wind, takeoff: Takeoff): BitmapDescriptor {
   return BitmapDescriptorFactory.fromResource(MarkerIconResource(wind, takeoff))
}

fun MarkerIconResource(wind: Wind, takeoff: Takeoff): Int {
    return if (isDegreeBetween((wind.direction?: 0.0).toDouble(), takeoff.greenStart, takeoff.greenStop)) {
        R.drawable.greendot
    } else {
        R.drawable.red_dot
    }
}

fun markerColor(wind: Wind, takeoff: Takeoff): Float {
    return when(
        checkWindConditions(
            windSpeed = wind.speed,
            windDirection = wind.direction?.toDouble()?: 0.0,
            greenStart = takeoff.greenStart,
            greenStop = takeoff.greenStop
        )
    ) {
        WindCondition.GOOD -> HUE_GREEN
        WindCondition.BAD -> HUE_RED
        else -> HUE_YELLOW
    }
}