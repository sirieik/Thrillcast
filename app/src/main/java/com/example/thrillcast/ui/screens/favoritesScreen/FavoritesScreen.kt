
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
                items(favoriteUiState.value.favoriteList) {
                    if (it != null) {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        NowWeatherCard(
                            viewModel = weatherViewModel,
                            context = context,
                            takeoff = it
                        )
                    }
                }
            }
        }
    )
}