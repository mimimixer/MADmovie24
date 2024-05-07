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
import com.example.movieappmad24.viewmodels.HomeScreenViewModel

@Composable
fun HomeScreen(navController: NavController){ //,, moviesViewModel: MoviesViewModel) {

    /*
    val db = MovieDatabase.getDatabase(LocalContext.current)
    val repository = MovieRepository(movieDao = db.movieDao())
    val factory = MovieViewModelFactory(repository=repository)
    val viewModel : HomeScreenViewModel = viewModel(factory = factory)*/
    val viewModel: HomeScreenViewModel =
        viewModel(factory = InjectorUtils.provideMovieViewModelFactory(LocalContext.current, null))

    Scaffold(
        topBar = {
            SimpleTopAppBar("Movie App", false, navController)
        },
        bottomBar = {
            SimpleBottomAppBar(navController)
        }
    ) { values ->
        val moviesAsState by viewModel.movieListFlow.collectAsState()

        MovieList(
            values = values,
            movieWithImages =  moviesAsState,
            navController = navController,
            viewModel = viewModel
        )
    }
}