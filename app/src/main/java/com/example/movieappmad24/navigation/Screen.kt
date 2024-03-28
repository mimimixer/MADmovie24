package com.example.movieappmad24.navigation

sealed class Screen (val route: String) {
    object Home : Screen(route = "home_screen")
    object Detail: Screen(route = "detail_screen/{movieId}")
    object Watchlist: Screen(route = "watchlist_screen/{watchList}")

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { args ->
                append("/$args")
            }
        }
    }

}