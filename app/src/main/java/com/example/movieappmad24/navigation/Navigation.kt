package com.example.movieappmad24.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieappmad24.models.DETAIL_SCREEN_KEY
import com.example.movieappmad24.viewmodels.MoviesViewModel
import com.example.movieappmad24.models.Screen
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.screens.DetailScreen
import com.example.movieappmad24.screens.HomeScreen
import com.example.movieappmad24.screens.WatchlistScreen

@Composable
fun Navigation(){
    val navController =  rememberNavController()
    //var moviesViewModel : MoviesViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ){
        composable(route = Screen.Home.route)
        {
            HomeScreen(navController = navController)//, moviesViewModel = moviesViewModel)
        }
        composable(
            route = Screen.Detail.route + "/{$DETAIL_SCREEN_KEY}",
            arguments = listOf(
                navArgument(name = DETAIL_SCREEN_KEY) {
                    type = NavType.StringType
                })
        ) {backStackEntry ->
            val movietitle = getMovies()
                .find{ it.id.equals(backStackEntry.arguments?.getString(DETAIL_SCREEN_KEY))}?.title
            Log.d("Args", "$movietitle")
            println("detailscreenkey, also movieID is ${backStackEntry.arguments?.getString(DETAIL_SCREEN_KEY)}")
                DetailScreen(movieID = backStackEntry.arguments?.getString(DETAIL_SCREEN_KEY),
                    navController = navController
                )
            // moviesViewModel = moviesViewModel)
        }
        composable(route = Screen.Watchlist.route
            /*arguments = listOf(
                navArgument(name = "watchList"){
                    type = NavType.StringType
                    defaultValue = "default"
                    nullable = true
                })*/
        )
        {
            WatchlistScreen(navController = navController)//, moviesViewModel)
        }
    }

}


