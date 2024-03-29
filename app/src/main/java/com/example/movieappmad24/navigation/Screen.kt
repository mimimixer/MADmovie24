package com.example.movieappmad24.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.ui.graphics.vector.ImageVector

const val DETAIL_SCREEN_KEY = "movieId"
sealed class Screen(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
) {
    object Home : Screen(
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        route = "home_screen"
    )
    object Detail: Screen(
        title = "Detail Screen",
        selectedIcon = Icons.Default.Face,
        unselectedIcon = Icons.Default.Person,
        route = "detail_screen/{$DETAIL_SCREEN_KEY}"
    )
    object Watchlist: Screen(
        title = "Watchlist",
        selectedIcon = Icons.Filled.Star,
        unselectedIcon = Icons.Outlined.Star,
        route = "watchlist_screen"
    )
}


val bottomNavigationIcons = listOf(
    Screen.Home,
    Screen.Watchlist
)