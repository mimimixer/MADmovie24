package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.movieappmad24.composables.MovieCard
import com.example.movieappmad24.composables.PosterHorizontalScroll
import com.example.movieappmad24.composables.SimpleBottomAppBar
import com.example.movieappmad24.composables.SimpleTopAppBar
import com.example.movieappmad24.models.getMovies


@Composable
fun DetailScreen(movieId: String?, navController: NavController) {
    Scaffold(
        topBar = {
            SimpleTopAppBar(
                topBarText = "${getMovies().find { it.id == movieId }?.title}",
                backArrow = true,
                navController = navController
            )
        },
        bottomBar = {
            SimpleBottomAppBar(navController)
        }
    ) { values ->
        /*Text(
                modifier = Modifier.clickable {
                    // navController.popBackStack()
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) {
                            inclusive = true
                        }
                    }
                },
                text = "Hello detailscreen $movieId",
                color = Color.Red,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge
            )*/
        getMovies().find { it.id == movieId }?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(values)
            ) {
                MovieCard(movie = it) //this is what is called MovieRow in the exercises
                PosterHorizontalScroll(movie = it, sizeDp = 250, contentScaleVar = ContentScale.Crop)
            }
        }
    }
}
