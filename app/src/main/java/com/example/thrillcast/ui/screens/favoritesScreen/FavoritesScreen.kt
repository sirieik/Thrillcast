package com.example.thrillcast.ui.screens.favoritesScreen
import com.example.thrillcast.ui.viewmodels.BottomSheetViewModel
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.thrillcast.ui.common.NowWeatherCard
import com.example.thrillcast.ui.theme.DarkBlue
import com.example.thrillcast.ui.theme.Silver
import com.example.thrillcast.ui.viewmodels.favorites.FavoriteViewModel
import com.example.thrillcast.ui.viewmodels.map.MapViewModel
import com.example.thrillcast.ui.viewmodels.weather.WeatherViewModel

/**
 * @Composable funksjon for å vise favorittskjermen. Den representerer en side der brukere kan se
 * deres favorittsteder og deres nåværende værforhold.
 *
 * Denne funksjonen samler tilstander fra de gitte viewmodellene og genererer brukergrensesnittet basert på de
 * samlede tilstandene. Den håndterer også brukerinteraksjon og navigerer tilbake til kartskjermen med utvidet
 * bottomsheet om valgt sted når et favorittsted blir klikket på.
 *
 * @param favoriteViewModel ViewModel for å håndtere brukergrensesnittet relatert til brukerens favorittsteder.
 * @param weatherViewModel ViewModel for å håndtere brukergrensesnittet relatert til værinformasjon.
 * @param mapViewModel ViewModel for å håndtere brukergrensesnittet relatert til kartvisningen.
 * @param navigateBack Funksjon for å håndtere navigasjon tilbake til forrige skjerm.
 * @param bottomSheetViewModel ViewModel for å utvide bottomSheet-et i kartskjermen når man trykker på et NowWeatherCard.
 * @param context Konteksten der denne funksjonen blir kalt og satt sammen.
 *
 * @OptIn(ExperimentalMaterial3Api::class) Indikerer at denne funksjonen bruker eksperimentell API
 * fra Material3.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    favoriteViewModel: FavoriteViewModel,
    weatherViewModel: WeatherViewModel,
    mapViewModel: MapViewModel,
    navigateBack: () -> Unit,
    bottomSheetViewModel: BottomSheetViewModel,
    context: Context
) {
    val favoriteUiState = favoriteViewModel.favoriteUiState.collectAsState()
    weatherViewModel.retrieveFavoritesWeather(favorites = favoriteUiState.value.favoriteList)

    val multiCurrentWeatherUiState = weatherViewModel.multiCurrentWeatherUiState.collectAsState()
    val favoriteAndCurrentWeatherMap = favoriteUiState.value.favoriteList.zip(multiCurrentWeatherUiState.value.currentWeatherList)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            FavoritesTopBar(navigateBack)
        },
        content = { paddingValues ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(favoriteAndCurrentWeatherMap){
                    if(it.first != null) {
                        Text(
                            text = it.first?.name ?: "",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        NowWeatherCard(
                            windDirection = it.second.wind?.direction ?: 0,
                            speed = it.second.wind?.speed ?: 0.0,
                            unit = it.second.wind?.unit ?: "m/s",
                            gust = it.second.wind?.gust ?: 0.0,
                            symbolCode = it.second.nowCastObject?.data?.next_1_hours?.summary?.symbol_code
                                ?: "sleetshowersandthunder_polartwilight",
                            temperature = it.second.nowCastObject?.data?.instant?.details?.air_temperature
                                ?: 0.0,
                            greenStart = it.first?.greenStart ?: 0,
                            greenStop = it.first?.greenStop ?: 0,
                            context = context,
                            onClick = {

                                // Oppdater valgt takeoff-spot i vær-ViewModelen
                                weatherViewModel.updateChosenTakeoff(it.first!!)

                                // Hent fremtidig vær data a for den valgte takeoff-spoten
                                weatherViewModel.retrieveForecastWeather(it.first!!)

                                // Hent nåværende værdata for den valgte takeoff-spoten
                                weatherViewModel.retrieveCurrentWeather(it.first!!)

                                // Hent høydevind-data for den valgte takeoff-spoten
                                weatherViewModel.retrieveHeightWind(it.first!!)

                                bottomSheetViewModel.expandBottomSheet()

                                navigateBack()
                            }
                        )
                    }
                }
            }
        }
    )
}

/**
 * @Composable funksjon som representerer topBar-en i favorittskjermen. TopBar-en inneholder en
 * tilbake-knapp for navigasjon, og en titteltekst.
 *
 * Tilbake-knappen er posisjonert til venstre, og ved klikk vil det utløse navigateBack funksjonen.
 * Tittelteksten, "Favourites", er posisjonert i midten av topplinjen.
 *
 * @param navigateBack Funksjonen som blir kalt når tilbake-knappen klikkes. Den er ansvarlig for
 * navigasjonen tilbake til forrige skjerm.
 */
@Composable
fun FavoritesTopBar(navigateBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkBlue)
            .height(60.dp)
            .border(1.dp, color = Silver, RectangleShape)
    ) {
        IconButton(
            onClick = navigateBack,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back Icon",
                tint = Silver,
                modifier = Modifier.size(30.dp)
            )
        }

        Text(
            text = "Favourites",
            style = MaterialTheme.typography.titleSmall,
            color = Silver,
            fontSize = 30.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}