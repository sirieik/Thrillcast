
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.thrillcast.ui.NavItem
import com.example.thrillcast.ui.screens.introScreen2.IntroScreen2


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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavigationGraph( navController: NavHostController ){
    NavHost(navController, startDestination = "introscreen") {
        composable(NavItem.settings.route) { SettingsScreen() }
        composable(NavItem.map.route) { MapModBotSheet(navigateBack = { navController.navigate("introscreen")})}
        composable(NavItem.favorites.route) { FavoritesScreen() }
        composable("introscreen") { IntroScreen2(onNavigate = { navController.navigate(NavItem.map.route)})}
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
        modifier = Modifier.height(60.dp),
        content = {
            navItems.forEachIndexed{ index, item ->
                NavigationBarItem(
                    icon = { Icon(item.icon, contentDescription = item.name) },
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