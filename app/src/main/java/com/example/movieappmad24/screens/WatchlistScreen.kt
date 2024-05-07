package com.example.movieappmad24.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieappmad24.viewmodels.DependencyInjection.InjectorUtils
import com.example.movieappmad24.composables.MovieList
import com.example.movieappmad24.composables.SimpleBottomAppBar
import com.example.movieappmad24.composables.SimpleTopAppBar
import com.example.movieappmad24.data.MovieDatabase
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.viewmodels.MoviesViewModel
import com.example.movieappmad24.viewmodels.MovieViewModelFactory
import com.example.movieappmad24.viewmodels.WatchlistScreenViewModel

@Composable
fun WatchlistScreen(navController: NavController){ //,, moviesViewModel: MoviesViewModel) {
/*
    val db = MovieDatabase.getDatabase(LocalContext.current)
    val repository = MovieRepository(movieDao = db.movieDao())
    val factory = MovieViewModelFactory(repository=repository)
    val viewModel : WatchlistScreenViewModel = viewModel(factory = factory)
 */
    val viewModel: WatchlistScreenViewModel =
        viewModel(factory = InjectorUtils.provideMovieViewModelFactory(LocalContext.current, null))

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
        val moviesState by viewModel.movieListFlow.collectAsState()

        if(moviesState.isEmpty()){
            MovieList(
                values = values,
                movieWithImages = moviesState,
                navController = navController,
                viewModel = viewModel
            )
        }
        MovieList(
            values = values,
            movieWithImages = moviesState,
            navController = navController,
            viewModel = viewModel
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


