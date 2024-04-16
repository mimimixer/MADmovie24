package com.example.movieappmad24.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.movieappmad24.composables.MovieList
import com.example.movieappmad24.composables.SimpleBottomAppBar
import com.example.movieappmad24.composables.SimpleTopAppBar
import com.example.movieappmad24.models.MoviesViewModel

@Composable
fun WatchlistScreen(navController: NavController, moviesViewModel: MoviesViewModel) {
    Scaffold(
        topBar = {
            SimpleTopAppBar("My Watchlist", false, navController)
        },
        bottomBar = {
            SimpleBottomAppBar(navController)
        }
    ) { values ->
        //  if (getWatchlistMovies().isNullOrEmpty()) {
        //please don't get confused: MovieList is rendering a list of movies
        // using MovieRow (called MovieCard in my app)
        /*   Column (
               modifier = Modifier
                   .fillMaxSize()
                   .padding(values)
           ){*/
        //val listOfMovs = getMovies()
        if(moviesViewModel.watchList.isEmpty()){
            MovieList(
                values = values,
                movies = moviesViewModel.noWatchList,
                navController = navController
            )
        }
        MovieList(
            values = values,
            movies = moviesViewModel.watchList,
            navController = navController
        )
        //MovieCard(movie = getDefaultMovies()[0])
        //PosterHorizontalScroll(movie = getDefaultMovies()[0], sizeDp = 380, ContentScale.Crop)
        /*    }
        } else {
            getWatchlistMovies()?.let {
                MovieList(
                    values = values,
                    movies = it,
                    navController = navController
                )
            }
        }*/
    }
}


