
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
import com.example.thrillcast.ui.viemodels.favorites.FavoriteViewModel
import com.example.thrillcast.ui.viemodels.weather.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    favoriteViewModel: FavoriteViewModel,
    weatherViewModel: WeatherViewModel,
    onNavigate: () -> Unit,
    context: Context
) {
    val favoriteUiState = favoriteViewModel.favoriteUiState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(
                    onClick = onNavigate
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back Icon",
                        //tint = Silver
                    )
                }

                Text(
                    text = "Favorites",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(favoriteUiState.value.favoriteList) {
                        if (it != null) {
                            NowWeatherCard(
                                viewModel = weatherViewModel,
                                context = context,
                                takeoff = it,
                                topText = it.name
                            )
                        }
                    }
                }
            }
        }
    )
}