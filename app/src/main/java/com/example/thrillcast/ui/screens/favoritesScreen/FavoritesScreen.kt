
import android.content.Context
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
import androidx.compose.ui.unit.dp
import com.example.thrillcast.ui.viemodels.favorites.FavoriteViewModel
import com.example.thrillcast.ui.viemodels.weather.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    favoriteViewModel: FavoriteViewModel,
    weatherViewModel: WeatherViewModel,
    navigateBack: () -> Unit,
    context: Context
) {
    val favoriteUiState = favoriteViewModel.favoriteUiState.collectAsState()
    weatherViewModel.retrieveFavoritesWeather(favorites = favoriteUiState.value.favoriteList)

    val multiCurrentWeatherUiState = weatherViewModel.multiCurrentWeatherUiState.collectAsState()

    val favoriteAndCurrentWeatherMap = favoriteUiState.value.favoriteList.zip(multiCurrentWeatherUiState.value.currentWeatherList)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = navigateBack
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back Icon",
                        //tint = Silver
                    )
                }

                Text(
                    text = "Favorites",
                    modifier = Modifier.align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        },

        content = { paddingValues ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                //items(favoriteUiState.value.favoriteList) {
                items(favoriteAndCurrentWeatherMap){
                    if (it != null) {
                        Text(
                            text = it.first?.name ?: "",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        NowWeatherCard(
                            context = context,
                            takeoff = it.first,
                            wind = it.second.wind,
                            weather = it.second.nowCastObject
                        )
                    }
                }
            }
        }
    )
}