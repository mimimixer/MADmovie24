package com.example.movieappmad24.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.movieappmad24.models.WatchlistMovies
import com.example.movieappmad24.models.getDefaultMovies
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.models.getWatchlistMovies
import com.example.movieappmad24.models.watchlist
import com.example.movieappmad24.screens.DetailScreen
import com.example.movieappmad24.screens.HomeScreen
import com.example.movieappmad24.screens.Watchlist

@Composable
fun Navigation(navController: NavHostController){
//    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(route = Screen.Home.route)
        {
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.Detail.route + "/{movieId}",
            arguments = listOf(
                navArgument(name = "movieId") {
                type = NavType.StringType
            })
        ) {backStackEntry ->
            var movietitle = getMovies().find{ it.id == backStackEntry.arguments?.getString("movieId")}?.title
            Log.d("Args", "$movietitle")
            DetailScreen(movieId = backStackEntry.arguments?.getString("movieId"), navController)
        }
        composable(
            route = Screen.Watchlist.route,
            /*arguments = listOf(
                navArgument(name = "watchList"){
                    type = NavType.StringType
                    defaultValue = "default"
                    nullable = true
                })*/
            )
        {
            Watchlist(navController = navController)
        }
    }

}


