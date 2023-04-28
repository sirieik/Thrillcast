
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.thrillcast.ui.viemodels.weather.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(addedFavorites: List<WeatherViewModel>, weatherViewModel: WeatherViewModel = viewModel()) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {  },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Text(text = "Favorites")
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    /*items(addedFavorites) {
                        NowWeatherCard(viewModel = weatherViewModel)
                    }*/
                }
            }
        }
    )
}