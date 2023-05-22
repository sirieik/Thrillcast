

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
import com.example.thrillcast.ui.viewmodels.favorites.FavoriteViewModel
import com.example.thrillcast.ui.viewmodels.map.MapViewModel
import com.example.thrillcast.ui.viewmodels.weather.WeatherViewModel
import kotlinx.coroutines.launch
import java.util.*
import androidx.compose.material3.MaterialTheme

@SuppressLint("StateFlowValueCalledInComposition")
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

            //Hvis stedet er lagt til i favoritter er hjertet-ikonet filled, hvis ikke er det hult
            var fillState by remember {
                mutableStateOf(false)
            }
            fillState = favoriteUiState.value.favoriteList.contains(takeoffUiState.value.takeoff)

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
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W900),
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
                            fillState = !fillState
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
                        val iconImageVector = if (fillState) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder

                        Icon(
                            imageVector = iconImageVector,
                            contentDescription = "Favorites button",
                            modifier = Modifier
                                .padding(8.dp)
                                .size(48.dp),
                            tint = Color.Magenta
                        )
                    }
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                modalSheetState.hide()
                                mapViewModel.updateChosenTakeoff(null)
                            }
                        },
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
                                    text = title,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = if (tabState == index) FontWeight.Bold else FontWeight.Normal
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
                        1 -> NowPage(weatherViewModel = weatherViewModel, context = context)
                        else -> FuturePage(weatherViewModel = weatherViewModel, context = context)// it may need changes
                    }
                }
            }
            LaunchedEffect(bottomSheetViewModel.bottomSheetState.value) {
                modalSheetState.animateTo(bottomSheetViewModel.bottomSheetState.value)
            }
        }
    ) {
        MapScreenContent(
            coroutineScope = coroutineScope,
            modalSheetState = modalSheetState,
            mapViewModel = mapViewModel,
            weatherViewModel = weatherViewModel,
            searchBarViewModel = searchBarViewModel,
            context = context,
            onNavigate = onNavigate
        )
    }
}



