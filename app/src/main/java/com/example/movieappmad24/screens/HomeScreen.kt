package com.example.movieappmad24.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.movieappmad24.composables.MovieList
import com.example.movieappmad24.composables.SimpleBottomAppBar
import com.example.movieappmad24.composables.SimpleTopAppBar
import com.example.movieappmad24.models.getMovies

@Composable
fun HomeScreen (navController: NavController) {
    Scaffold(
        topBar = {
            SimpleTopAppBar("Movie App", false, navController)
        },
        bottomBar = {
            SimpleBottomAppBar(navController)
        }
    ) { values ->
        MovieList(
            values = values,
            movies = getMovies(),
            navController = navController
        )
    }
}