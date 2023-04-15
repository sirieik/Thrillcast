
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thrillcast.ui.screens.mapScreen.MapScreen
import com.example.thrillcast.ui.screens.mapScreen.MapViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MapModBotSheet(
    mapViewModel: MapViewModel = viewModel(),
    holfuyWeatherViewModel: HolfuyWeatherViewModel = viewModel(),
    navigateBack: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded },
        skipHalfExpanded = true
    )
    val HFUiState = holfuyWeatherViewModel.uiState.collectAsState()
    var currentSheet by remember { mutableStateOf<SheetPage>(SheetPage.Info) }

    ModalBottomSheetLayout(
        sheetState = modalSheetState,
        sheetContent = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ChangePageButton(
                        text = "Info",
                        isSelected = currentSheet == SheetPage.Info,
                        onClick = { currentSheet = SheetPage.Info }
                    )
                    ChangePageButton(
                        text = "Now",
                        isSelected = currentSheet == SheetPage.Now,
                        onClick = { currentSheet = SheetPage.Now }
                    )
                    ChangePageButton(
                        text = "Future",
                        isSelected = currentSheet == SheetPage.Future,
                        onClick = {currentSheet = SheetPage.Future}
                    )
                    IconButton(
                        onClick = { coroutineScope.launch { modalSheetState.hide() } },
                        //modifier = Modifier.align(Alignment.End)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close info sheet"
                        )
                    }
                }

                when(currentSheet) {
                    is SheetPage.Info -> InfoPage(holfuyWeatherViewModel = holfuyWeatherViewModel)
                    is SheetPage.Now -> NowPage(holfuyWeatherViewModel = holfuyWeatherViewModel)
                    else -> FuturePage(holfuyWeatherViewModel = holfuyWeatherViewModel)// it may need changes
                }
            }
        }
    ) {
        MapScreen(
            coroutineScope = coroutineScope,
            modalSheetState,
            mapViewModel = mapViewModel,
            holfuyWeatherViewModel = holfuyWeatherViewModel,
            navigateBack
        )
    }
}

@Composable
fun ChangePageButton(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body1.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        )
    }
}

@Composable
fun NowPage(holfuyWeatherViewModel: HolfuyWeatherViewModel) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd
    ) {
        Column() {
            val HFUiState = holfuyWeatherViewModel.uiState.collectAsState()
            HFUiState.value.wind.direction?.let {
                WindDirectionWheel(
                    greenStart = HFUiState.value.takeoff.greenStart,
                    greenStop = HFUiState.value.takeoff.greenStop,
                    windDirection = it,
                )
            }
            val unit = HFUiState.value.wind.unit
            val speed = HFUiState.value.wind.speed
            val gust = HFUiState.value.wind.gust

            HFUiState.value.wind.unit?.let {
                Text(text = "$speed($gust) $unit")
            }
        }
    }
}

@Composable
fun InfoPage(holfuyWeatherViewModel: HolfuyWeatherViewModel) {

    val HFUiState = holfuyWeatherViewModel.uiState.collectAsState()
        Text(text = "PP2")
    HFUiState.value.takeoff.coordinates?.let{
        Text(text = "Coordinate: ${it.latitude}, ${it.longitude}")

    }
    HFUiState.value.takeoff.moh?.let{
        Text(text = "MOH: $it")
    }

    //Text(text = "INFO")
}
@Composable
//Maybe we can change to "Tomorrow" instead of "future"? for the buttoom-name??
fun FuturePage(holfuyWeatherViewModel: HolfuyWeatherViewModel) {
    val HFUiState = holfuyWeatherViewModel.uiState.collectAsState()

    //HFUiState.value.weatherForecast.next_1_hour.summary.symbol_code
    /**
     * import androidx.compose.foundation.lazy.items
     */
    LazyColumn {
        items(HFUiState.value.weatherForecast) { weatherForecast ->
            val time = weatherForecast.time.toLocalTime()
            val text = weatherForecast.data?.next_1_hours?.summary?.symbol_code ?: ""
            Text(text = "${time}  : ${text}")
        }
    }
    Text(text = "FUTURE")

}

/*
@Composable
fun AlpacaCard(alpacaParty: AlpacaParty){

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    )  {
        Column(modifier = Modifier
            .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            Spacer(modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
                .background(color= Color(alpacaParty.color.toColorInt()))
            )

            Text(text = alpacaParty.name)
            //image
            AsyncImage(
                model = alpacaParty.img,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )

            androidx.compose.material3.Text(text = "Leader: "+alpacaParty.leader)
            androidx.compose.material3.Text(text = "Votes:"+ alpacaParty.votes +"  - "+alpacaParty.percentage + "%")
        }

    }

}

*/


