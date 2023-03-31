import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.thrillcast.ui.NavItem



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThrillCastApp(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { NavBar(navController = navController) }
    ) {
            innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavigationGraph(navController = navController)
        }
    }
}

@Composable
fun NavigationGraph( navController: NavHostController ){
    NavHost(navController, startDestination = NavItem.map.route) {
        composable(NavItem.settings.route) { SettingsScreen() }
        composable(NavItem.map.route) { MapScreen() }
        composable(NavItem.favorites.route) { FavoritesScreen() }
    }
}

@Composable
fun NavBar(navController: NavHostController) {

    var selectedItem by remember { mutableStateOf(1) }

    val navItems = listOf(
        NavItem.settings,
        NavItem.map,
        NavItem.favorites,
    )

    NavigationBar(
        content = {
            navItems.forEachIndexed{ index, item ->
                NavigationBarItem(
                    icon = { Icon(item.icon, contentDescription = item.name) },
                    label = { Text(item.name) },
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index

                        navController.navigate(item.route) {

                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    )
}