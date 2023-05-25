package com.example.thrillcast.ui.screens.mapScreen


import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.thrillcast.data.datamodels.Wind
import com.example.thrillcast.ui.common.Takeoff
import com.example.thrillcast.ui.common.WindCondition
import com.example.thrillcast.ui.common.calculations.checkWindConditions
import com.example.thrillcast.ui.theme.DarkBlue
import com.example.thrillcast.ui.theme.Silver
import com.example.thrillcast.ui.viewmodels.map.MapViewModel
import com.example.thrillcast.ui.viewmodels.map.SearchBarViewModel
import com.example.thrillcast.ui.viewmodels.map.UserAction
import com.example.thrillcast.ui.viewmodels.weather.WeatherViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @Composable funksjon som representerer innholdet på kartskjermen.
 * Den viser et kart, en topBar og et søkefelt basert på tilstanden, og håndterer
 * navigasjon og interaksjon med markører.
 *
 * @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class) - Funksjonen bruker den eksperimentelle Material 3 API og Material API.
 *
 * @param coroutineScope CoroutineScope hvor coroutinene skal lanseres.
 * @param modalSheetState Tilstanden til modalBottomSheet.
 * @param mapViewModel En ViewModel assosiert med kartskjermen.
 * @param weatherViewModel En ViewModel assosiert med værdata.
 * @param searchBarViewModel En ViewModel assosiert med søkefelt-skjermen.
 * @param onNavigate En lambda-funksjon som utføres for å håndtere navigasjon.
 *
 * Denne funksjonen initialiserer kartet med "kameraet" fokusert på Norge, og setter markører på kartet for Takeoff-lokasjoner.
 * Når en markør eller et søkeresultat blir klikket, fokuserer kartets kamera på den valgte plasseringen og
 * et modalBottomSheet med informasjon popper opp.
 * Innholdet hentes ved hjelp av handleTakeoffSelection-metoden.
 * TopBaren kan veksle mellom å vise et søkefelt eller en vanlig topBar basert på tilstanden til searchBarViewModel.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MapScreenContent(
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
                        mapViewModel,
                        searchBarViewModel
                    ) { takeoff ->
                        selectedSearchItem = takeoff
                        cameraPositionState.position = CameraPosition.Builder()
                            .target(selectedSearchItem!!.coordinates)
                            .zoom(9f)
                            .build()
                        coroutineScope.launch {
                            if (modalSheetState.isVisible) {
                                modalSheetState.hide()
                            } else {
                                handleTakeoffSelection(
                                    takeoff,
                                    weatherViewModel,
                                    modalSheetState
                                )
                            }
                        }
                    }
                } else {
                    TopBar(
                        onSearchIconClick = {
                            searchBarViewModel.onAction(UserAction.SearchIconClicked)
                        },
                        onNavigate
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
                            icon = defaultMarker(markerColor(wind = wind, greenStart = takeoff.greenStart, greenStop = takeoff.greenStop)),
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
                                        handleTakeoffSelection(
                                            takeoff,
                                            weatherViewModel,
                                            modalSheetState
                                        )
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

/**
 * @Composable funksjon som representerer en TopBar med favoritt- og søkeikon, og tittelen "Paragliding".
 *
 * @param onSearchIconClick En lambda-funksjon som utføres når søkeikonet blir klikket.
 * @param onNavigate En lambda-funksjon som utføres når favorittikonet blir klikket.
 *
 * TopBar-en er strukturert som følger:
 * - En rad med en mørkeblå bakgrunn og sølvfarget kant, som fyller hele skjermens bredde.
 * - Et favorittikon på venstre side, som utløser onNavigate-funksjonen når det blir klikket.
 * - En tittel "Paragliding" i midten.
 * - Et søkeikon på høyre side, som utløser onSearchIconClick-funksjonen når det blir klikket.
 */
@Composable
fun TopBar(
    onSearchIconClick: () -> Unit,
    onNavigate: () -> Unit,
) {

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
            onClick = onNavigate,

        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Favorite Icon",
                tint = Silver,
                modifier = Modifier.size(30.dp)
                //modifier = Modifier.size(48.dp).padding(8.dp)
            )
        }

        Text(
            text = "Paragliding", style = MaterialTheme.typography.titleMedium,
            color = Silver,
        )

        IconButton(
            onClick = onSearchIconClick
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon",
                tint = Silver,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}


/**
 * Håndterer valg av ny takeoffspot fra kartet. Kaller på viewmodel for å hente de nødvendige dataene
 * for fremstilling av værdata. Utvider bottomsheeten med værdata.
 *
 * @param takeoff Den valgte takeoff-spoten.
 * @param weatherViewModel ViewModelen ansvarlig for værrelaterte operasjoner.
 * @param modalSheetState Tilstanden til bottomsheet.
 */
@OptIn(ExperimentalMaterialApi::class)
suspend fun handleTakeoffSelection(
    takeoff: Takeoff,
    weatherViewModel: WeatherViewModel,
    modalSheetState: ModalBottomSheetState
) {

    // Oppdater valgt takeoff-spot i vær-ViewModelen
    weatherViewModel.updateChosenTakeoff(takeoff)

    // Hent fremtidig vær data a for den valgte takeoff-spoten
    weatherViewModel.retrieveForecastWeather(takeoff)

    // Hent nåværende værdata for den valgte takeoff-spoten
    weatherViewModel.retrieveCurrentWeather(takeoff)

    // Hent høydevind-data for den valgte takeoff-spoten
    weatherViewModel.retrieveHeightWind(takeoff)

    // Animer bottomsheet til utvidet tilstand
    modalSheetState.animateTo(ModalBottomSheetValue.Expanded)
}


/**
 * Returnerer fargeverdien for markøren basert på vindforholdene og området for den grønne sonen
 * i vindretningssirkelen for takeoff-lokasjonen.
 *
 * @param wind Vinddata for lokasjonen.
 * @param greenStart Startverdien for den grønne sonen i vindretningssirkelen.
 * @param greenStop Sluttverdien for den grønne sonen i vindretningssirkelen.
 * @return Fargeverdien for markøren som en float, grønn hvis bra, rød hvis dårlig og gul hvis ok.
 */
fun markerColor(wind: Wind, greenStart: Int, greenStop: Int): Float {
    return when(
        checkWindConditions(
            windSpeed = wind.speed,
            windDirection = wind.direction?.toDouble()?: 0.0,
            greenStart = greenStart,
            greenStop = greenStop
        )
    ) {
        WindCondition.GOOD -> HUE_GREEN
        WindCondition.BAD -> HUE_RED
        else -> HUE_YELLOW
    }
}