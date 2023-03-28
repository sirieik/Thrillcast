import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun ThrillCastApp(){
    val navController = rememberNavController()

}
/*
@Composable
fun ThrillCastNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "map"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("map") {
            MapScreen(
                onNavigateToFavorites = { navController.navigate("favorites") },
                onNavigateToSettings = { navController.navigate("settings") }
            )
        }
        composable("settings") {
            SettingsScreen(
                onNavigateToFavorites = { navController.navigate("favorites") },
                onNavigateToMap = { navController.navigate("map") }
            )
        }
        composable("favorites") {
            FavoritesScreen(
                onNavigateToMap = {navController.navigate("map")},
                onNavigateToSettings = {navController.navigate("settings")}
            )
        }
    }
}
*/



@Composable
fun NavBar() {
    var selectedItem by remember { mutableStateOf(0) }
    NavigationBar(

    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
            selected = selectedItem == 0,
            onClick = { selectedItem = 0 }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Map") },
            label = { Text("Map") },
            selected = selectedItem == 1,
            onClick = { selectedItem = 1 }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favorites") },
            label = { Text("Favorites") },
            selected = selectedItem == 2,
            onClick = { selectedItem = 2 }
        )
    }
}