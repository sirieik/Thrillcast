

//import androidx.compose.material.icons.Icons
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thrillcast.ui.screens.mapScreen.MapScreenContent
import com.example.thrillcast.ui.screens.mapScreen.SearchBarViewModel
import com.example.thrillcast.ui.theme.Red
import com.example.thrillcast.ui.theme.Silver
import com.example.thrillcast.ui.viemodels.favorites.FavoriteViewModel
import com.example.thrillcast.ui.viemodels.map.MapViewModel
import com.example.thrillcast.ui.viemodels.weather.WeatherViewModel
import kotlinx.coroutines.launch
import java.util.*

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    mapViewModel: MapViewModel = viewModel(),
    weatherViewModel: WeatherViewModel,
    searchBarViewModel: SearchBarViewModel = viewModel(),
    favoriteViewModel: FavoriteViewModel,
    onNavigate: () -> Unit,
    context: Context
) {

    val takeoffUiState = weatherViewModel.takeoffUiState.collectAsState()
    val favoriteUiState = favoriteViewModel.favoriteUiState.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )

    val tabList = listOf("Info", "Today", "Future")

    var tabState by remember {
        mutableStateOf(1)
    }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetContent = {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.1f, true)
                ) {
                    takeoffUiState.value.takeoff?.let {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.W900),
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .padding(start = 20.dp)
                                .weight(0.8f, true),
                            color = Color.Black
                        )
                    }
                    IconButton(
                        onClick = {
                            if(!favoriteUiState.value.favoriteList.contains(takeoffUiState.value.takeoff)) {
                                takeoffUiState.value.takeoff?.let {
                                    favoriteViewModel.addFavorite(
                                        it
                                    )
                                }
                            } else {
                                takeoffUiState.value.takeoff?.let {
                                    favoriteViewModel.removeFavorite(
                                        it
                                    )
                                }
                            }
                        }
                    ) {
                        //Sjekke hvis stedet er i favoritter er hjertet ikonet filled, hvis ikke er det hult
                        val isFavorite = favoriteUiState.value.favoriteList.contains(takeoffUiState.value.takeoff)
                        val iconImageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder
                        val iconOutlineTint = Color.Magenta
                        val iconFillTint = if (isFavorite) Color.Magenta else Color.White
                        Icon(
                            imageVector = iconImageVector,
                            contentDescription = "Favorites button",
                            modifier = Modifier
                                .padding(8.dp)
                                .size(48.dp),
                            tint = if (isFavorite) iconFillTint else iconOutlineTint
                        )
                    }
                    IconButton(
                        onClick = { coroutineScope.launch { modalSheetState.hide() } },
                        modifier = Modifier
                            .weight(0.2f, true)
                            .padding(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close info sheet"
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
                                    text = title, maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 20.sp, color = Silver
                                )
                            }
                        )
                    }
                }
                Column(
                    modifier = Modifier.weight(0.7f)
                ) {
                    when (tabState) {
                        0 -> InfoPage(weatherViewModel = weatherViewModel)
                        1 -> NowPage(weatherViewModel = weatherViewModel, context = context)
                        else -> FuturePage(weatherViewModel = weatherViewModel, context = context)// it may need changes
                    }
                }
            }

        }
    ) {
        MapScreenContent(
            coroutineScope = coroutineScope,
            modalSheetState,
            mapViewModel = mapViewModel,
            weatherViewModel = weatherViewModel,
            searchBarViewModel = searchBarViewModel,
            context = context,
            onNavigate
        )
    }
}

fun isDegreeBetween(value: Double, min: Int, max: Int): Boolean {
    val valueRadians = Math.toRadians(value)
    val minRadians = Math.toRadians(min.toDouble())
    val maxRadians = Math.toRadians(max.toDouble())

    return if (minRadians <= maxRadians) {
        valueRadians in minRadians..maxRadians
    } else {
        valueRadians >= minRadians || valueRadians <= maxRadians
    }
}

fun isDirectionValid(direction: Int, greenStart: Int, greenStop: Int): Boolean {
    return if(greenStart > greenStop) {
         (direction in greenStart .. 360 || direction in 0..greenStop)
    } else (direction in greenStart..greenStop)
}
