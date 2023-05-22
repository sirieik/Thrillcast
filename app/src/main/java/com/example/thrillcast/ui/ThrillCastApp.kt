package com.example.thrillcast.ui
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.thrillcast.ui.common.DisclaimerDialog
import com.example.thrillcast.ui.screens.favoritesScreen.FavoritesScreen
import com.example.thrillcast.ui.screens.mapScreen.MapScreen
import com.example.thrillcast.ui.viewmodels.BottomSheetViewModel
import com.example.thrillcast.ui.viewmodels.favorites.FavoriteViewModel
import com.example.thrillcast.ui.viewmodels.map.MapViewModel
import com.example.thrillcast.ui.viewmodels.weather.WeatherViewModel

/**
 * @Composable funksjon som representerer hovedgrensesnittet for ThrillCastApp.
 *
 * Denne funksjonen oppretter hoved-UI-komponentene i applikasjonen inkludert disclaimer-dialogen og en "scaffold",
 * innenfor det oppretter den en NavigationGraph for å navigere gjennom de forskjellige skjermene i appen.
 *
 * Denne funksjonen er merket med `@OptIn(ExperimentalMaterial3Api::class)`-annoteringen, noe som betyr at den bruker API-er
 * som er markert som eksperimentelle i Material 3-biblioteket.
 *
 * @param context Konteksten der applikasjonen for øyeblikket kjører.
 *
 * @see NavigationGraph
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThrillCastApp(context: Context){

    val navController = rememberNavController()

    DisclaimerDialog()

    Scaffold { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavigationGraph(navController = navController, context = context)
        }
    }
}

/**
 * @Composable funksjon som bygger navigasjonsgraf for applikasjonen.
 *
 * NavigationGraph-en kontrollerer navigasjonen mellom de forskjellige skjermene i appen. Den definerer to hovedskjermer:
 * MapScreen og FavoritesScreen, og håndterer navigasjonen mellom dem.
 *
 * Hver skjerm har tilgang til forskjellige viewmodeller for å hente og kontrollere dataene de trenger.
 *
 * @param navController NavHostController som kontrollerer navigasjonen mellom de forskjellige skjermene.
 * @param context Konteksten der applikasjonen for øyeblikket kjører.
 *
 * @see MapScreen
 * @see FavoritesScreen
 */
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