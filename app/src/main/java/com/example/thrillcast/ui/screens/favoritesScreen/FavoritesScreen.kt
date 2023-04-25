import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.thrillcast.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {  },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Text(text = stringResource(id = R.string.favourites))
            }
        }
    )
}