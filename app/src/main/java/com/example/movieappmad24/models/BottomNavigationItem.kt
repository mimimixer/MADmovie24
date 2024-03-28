package com.example.movieappmad24.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.movieappmad24.navigation.Screen

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)

val bottomIcons = listOf(
    BottomNavigationItem(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        route = Screen.Home.route
    ),
    BottomNavigationItem(
        title = "Watchlist",
        selectedIcon = Icons.Filled.Star,
        unselectedIcon = Icons.Outlined.Star,
        route = Screen.Watchlist.route
    )
)