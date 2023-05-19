
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.thrillcast.ui.screens.mapScreen.DisclaimerDialog
import com.example.thrillcast.ui.viewmodels.favorites.FavoriteViewModel
import com.example.thrillcast.ui.viewmodels.map.MapViewModel
import com.example.thrillcast.ui.viewmodels.weather.WeatherViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThrillCastApp(context: Context){

    val navController = rememberNavController()

    DisclaimerDialog()

    Scaffold(
        //bottomBar = { NavBar(navController = navController) }
    ) {
            innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavigationGraph(navController = navController, context = context)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavigationGraph(navController: NavHostController, context: Context) {
    val favoriteViewModel: FavoriteViewModel = viewModel()
    val weatherViewModel: WeatherViewModel = hiltViewModel()
    val mapViewModel: MapViewModel = hiltViewModel()
    val bottomSheetViewModel: BottomSheetViewModel = viewModel()

    NavHost(navController, startDestination = "map") {
        composable("map") {
            MapScreen(
                onNavigate = {
                    navController.navigate("favorites") {
                        launchSingleTop
                    }
                },
                context = context,
                weatherViewModel = weatherViewModel,
                favoriteViewModel = favoriteViewModel,
                mapViewModel = mapViewModel,
                bottomSheetViewModel = bottomSheetViewModel
            )
        }
        composable("favorites") {
            FavoritesScreen(
                weatherViewModel = weatherViewModel,
                favoriteViewModel = favoriteViewModel,
                mapViewModel = mapViewModel,
                navigateBack = {
                    navController.navigateUp()
                },
                bottomSheetViewModel = bottomSheetViewModel,
                context = context
            )
        }
    }
}