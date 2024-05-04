package com.example.movieappmad24.screens

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieappmad24.composables.MovieList
import com.example.movieappmad24.composables.SimpleBottomAppBar
import com.example.movieappmad24.composables.SimpleTopAppBar
import com.example.movieappmad24.data.MovieDatabase
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.viewmodels.HomeScreenViewModel
import com.example.movieappmad24.viewmodels.MoviesViewModel
import com.example.movieappmad24.viewmodels.MovieViewModelFactory

@Composable
fun HomeScreen(navController: NavController){ //,, moviesViewModel: MoviesViewModel) {

    val db = MovieDatabase.getDatabase(LocalContext.current)
    val repository = MovieRepository(movieDao = db.movieDao())
    val factory = MovieViewModelFactory(repository=repository)
    val viewModel : HomeScreenViewModel = viewModel(factory = factory)

    Scaffold(
        topBar = {
            SimpleTopAppBar("Movie App", false, navController)
        },
        bottomBar = {
            SimpleBottomAppBar(navController)
        }
    ) { values ->
        val moviesAsState by viewModel.movieList.collectAsState()

        MovieList(
            values = values,
            movies = moviesAsState,
            navController = navController,
            viewModel = viewModel
        )
    }
}