package com.example.thrillcast.ui.screens.mapScreen
import com.example.thrillcast.ui.viewmodels.BottomSheetViewModel
import InfoPage
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thrillcast.ui.screens.mapScreen.bottomsheetpages.FuturePage
import com.example.thrillcast.ui.screens.mapScreen.bottomsheetpages.TodayPage
import com.example.thrillcast.ui.theme.Red
import com.example.thrillcast.ui.theme.Silver
import com.example.thrillcast.ui.viewmodels.favorites.FavoriteViewModel
import com.example.thrillcast.ui.viewmodels.map.MapViewModel
import com.example.thrillcast.ui.viewmodels.map.SearchBarViewModel
import com.example.thrillcast.ui.viewmodels.weather.WeatherViewModel
import kotlinx.coroutines.launch
import java.util.*

/**
 * @Composable funksjon som lager en kartskjerm med en modal bottom sheet med tre pages
 * for informasjon, dagens vær og fremtidig vær for valgt takeoff.
 *
 * @param mapViewModel ViewModel som gir data til kartet.
 * @param weatherViewModel ViewModel som gir værdata.
 * @param searchBarViewModel ViewModel som gir søkefunksjonalitet for appen.
 * @param favoriteViewModel ViewModel som gir funksjonalitet for å merke lokasjoner som favoritt.
 * @param onNavigate Funksjon som skal utføres når en navigasjonshendelse oppstår.
 * @param bottomSheetViewModel ViewModel som gir data for bunnskjemaet.
 * @param context Konteksten hvor denne komponenten opererer.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    mapViewModel: MapViewModel,
    weatherViewModel: WeatherViewModel,
    searchBarViewModel: SearchBarViewModel = viewModel(),
    favoriteViewModel: FavoriteViewModel,
    onNavigate: () -> Unit,
    bottomSheetViewModel: BottomSheetViewModel,
    context: Context
) {

    val takeoffUiState = weatherViewModel.takeoffUiState.collectAsState()
    val favoriteUiState = favoriteViewModel.favoriteUiState.collectAsState()
    val bottomSheetUiState = bottomSheetViewModel.bottomSheetUiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )

    val tabList = listOf("INFO", "TODAY", "FUTURE")

    var tabState by remember {
        mutableStateOf(1)
    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetElevation = 16.dp,
        sheetContent = {

            //Hvis stedet er lagt til i favoritter er hjertet-ikonet filled, hvis ikke er det hult
            favoriteViewModel.isFavorite(takeoff = takeoffUiState.value.takeoff)
            Surface(
                color = Color.White
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = LocalConfiguration.current.screenHeightDp.dp / 1.7f * 1.25f),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.1f, true),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        takeoffUiState.value.takeoff?.let {
                            Text(
                                text = it.name,
                                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                                fontSize = 24.sp,
                                //maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    //.width(IntrinsicSize.Max)
                                    .padding(start = 20.dp),
                                color = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.weight(0.8f))
                        FavoriteButton(
                            isFavorite = favoriteUiState.value.isFavorite,
                            onToggleFavorite = {
                                takeoffUiState.value.takeoff?.let {
                                    favoriteViewModel.toggleFavorite(
                                        it
                                    )
                                }
                            },
                        )
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    modalSheetState.hide()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close info sheet",
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }

                    TabRow(
                        selectedTabIndex = tabState,
                        backgroundColor = Red
                    ) {
                        tabList.forEachIndexed { index, title ->
                            Tab(
                                selected = tabState == index,
                                onClick = { tabState = index },
                                text = {
                                    Text(
                                        text = title,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        fontSize = 20.sp,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = if (tabState == index) FontWeight.SemiBold else FontWeight.Normal
                                        ),
                                        color = Silver
                                    )
                                },
                                enabled = tabState != index
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.weight(0.7f)
                    ) {
                        when (tabState) {
                            0 -> InfoPage(weatherViewModel = weatherViewModel)
                            1 -> TodayPage(weatherViewModel = weatherViewModel, context = context)
                            else -> FuturePage(
                                weatherViewModel = weatherViewModel,
                                context = context
                            )
                        }
                    }
                }

                //Denne gjør slik at bottomsheeten endrer seg når verdien i bottomsheetuistate
                //endrer seg i bottomsheetViewModel.
                //Den lar bottomsheeten utvide seg når man velger en lokasjon på favorittskjermen.
                LaunchedEffect(bottomSheetUiState.value) {
                    modalSheetState.animateTo(bottomSheetUiState.value)
                }
            }
        }
    ) {
        MapScreenContent(
            coroutineScope = coroutineScope,
            modalSheetState = modalSheetState,
            mapViewModel = mapViewModel,
            weatherViewModel = weatherViewModel,
            searchBarViewModel = searchBarViewModel,
            //context = context,
            onNavigate = onNavigate
        )
    }
}

/**
 * @Composable funksjon som lager en FavoriteButton. Denne knappen vil endre og den
 * favorittstatusen til en plassering når den klikkes.
 *
 * @param isFavorite Den nåværende favorittstatusen til plasseringen. Hvis true, vil knappen vise som "favorisert".
 * @param onToggleFavorite Funksjonen som skal utføres når favorittknappen blir klikket.
 */

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    val favoriteIcon = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder

    IconButton(
        onClick = { onToggleFavorite() },
        modifier = Modifier.size(48.dp),
        content = {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = favoriteIcon,
                    contentDescription = "Favorites button",
                    tint = Color.Magenta,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }
    )
}

