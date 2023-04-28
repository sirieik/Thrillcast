

//import androidx.compose.material.icons.Icons
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thrillcast.R
import com.example.thrillcast.ui.screens.mapScreen.MapScreenContent
import com.example.thrillcast.ui.viemodels.map.MapViewModel
import com.example.thrillcast.ui.screens.mapScreen.SearchBarViewModel
import com.example.thrillcast.ui.viemodels.weather.WeatherViewModel
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.util.*

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapScreen(
    mapViewModel: MapViewModel = viewModel(),
    weatherViewModel: WeatherViewModel = viewModel(),
    searchBarViewModel: SearchBarViewModel = viewModel(),
    navigateBack: () -> Unit,
    context: Context
) {
    val weatherUiState = weatherViewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )

    val tabList = listOf("Info", "Today", "Future")

    var tabState by remember {
        mutableStateOf(0)
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
                    Text(
                        text = weatherUiState.value.takeoff.name,
                        style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.W900),
                        fontSize = 18.sp,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .padding(start = 20.dp)
                            .weight(0.8f, true),
                        color = Color.Black
                    )
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
                TabRow(selectedTabIndex = tabState) {
                    tabList.forEachIndexed { index, title ->
                        Tab(
                            selected = tabState == index,
                            onClick = { tabState = index },
                            text = {
                                Text(
                                    text = title, maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 20.sp
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
                        else -> FuturePage(weatherViewModel = weatherViewModel)// it may need changes
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
            navigateBack
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
