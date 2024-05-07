package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieappmad24.viewmodels.DependencyInjection.InjectorUtils
import com.example.movieappmad24.composables.MovieCard
import com.example.movieappmad24.composables.PlayerTrailer
import com.example.movieappmad24.composables.PosterHorizontalScroll
import com.example.movieappmad24.composables.SimpleBottomAppBar
import com.example.movieappmad24.composables.SimpleTopAppBar
import com.example.movieappmad24.viewmodels.DetailScreenViewModel
import kotlinx.coroutines.launch


@Composable
fun DetailScreen(movieID: String?, navController: NavController){ //, moviesViewModel: MoviesViewModel) {

    /*
    val db = MovieDatabase.getDatabase(LocalContext.current)
    val repository = MovieRepository(movieDao = db.movieDao())
    val factory = MovieViewModelFactory(repository=repository)
    val viewModel : DetailScreenViewModel = viewModel(factory = factory)
     */
    val viewModel: DetailScreenViewModel =
        viewModel(factory = InjectorUtils.provideMovieViewModelFactory(LocalContext.current, movieID))
    val moviesState by viewModel.movieListFlow.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            moviesState.find { it.movie.id == movieID }?.movie?.let {
                SimpleTopAppBar(
                    topBarText = it.title,
                    backArrow = true,
                    navController = navController
                )
            }
        },
        bottomBar = {
            SimpleBottomAppBar(navController)
        }
    ) { values ->
        //val moviesState by viewModel.movieListFlow.collectAsState()
        println("moviesState is this : $moviesState")

        moviesState.find { it.movie.id == movieID }?.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(values),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    MovieCard(
                        movie = it.movie,
                        movieImage = it.movieImages,
                        onItemClick = {
                            //movieId -> //this is what is called MovieRow in the exercises
                            //navController.navigate(route = "${Screen.Detail.route}/$movieId")
                        },
                        onFavouriteClick = { movie ->
                            coroutineScope.launch{
                                viewModel.toggleFavoriteMovie(movie)
                            }
                        }
                    ) //this is what is called MovieRow in the exercises
                    PlayerTrailer(it.movie, viewModel)
                    PosterHorizontalScroll(movieImages = it.movieImages, sizeDp = 250)
                }
            }
        }
    }
}
