package com.example.thrillcast.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavItem (

    val name: String,
    val route: String,
    val icon: ImageVector

    ) {

    object settings: NavItem("Settings", "settings", Icons.Filled.Settings)
    object map: NavItem("Map", "map", Icons.Filled.Place)
    object favorites: NavItem("Favorites", "favorites", Icons.Filled.Favorite)

}