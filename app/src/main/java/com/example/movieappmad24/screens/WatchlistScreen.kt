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
import com.example.movieappmad24.composables.MovieList
import com.example.movieappmad24.composables.PosterHorizontalScroll
import com.example.movieappmad24.composables.SimpleBottomAppBar
import com.example.movieappmad24.composables.SimpleTopAppBar
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getDefaultMovies
import com.example.movieappmad24.models.getWatchlistMovies

@Composable
fun Watchlist(navController: NavController) {
    Scaffold(
        topBar = {
            SimpleTopAppBar("My Watchlist", false, navController)
        },
        bottomBar = {
            SimpleBottomAppBar(navController)
        }
    ) { values ->
        if (getWatchlistMovies().isNullOrEmpty()) {
            //please don't get confused: MovieList is rendering a list of movies
            // using MovieRow (called MovieCard in my app)
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(values)
            ){
            MovieList(
                values = values,
                movies = getDefaultMovies(),
                navController = navController
            )
                //MovieCard(movie = getDefaultMovies()[0])
                //PosterHorizontalScroll(movie = getDefaultMovies()[0], sizeDp = 380, ContentScale.Crop)
            }
        } else {
            getWatchlistMovies()?.let {
                MovieList(
                    values = values,
                    movies = it,
                    navController = navController
                )
            }
        }
    }
}
