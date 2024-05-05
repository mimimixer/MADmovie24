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
import com.example.movieappmad24.composables.MovieCard
import com.example.movieappmad24.composables.PlayerTrailer
import com.example.movieappmad24.composables.PosterHorizontalScroll
import com.example.movieappmad24.composables.SimpleBottomAppBar
import com.example.movieappmad24.composables.SimpleTopAppBar
import com.example.movieappmad24.data.MovieDatabase
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.viewmodels.MoviesViewModel
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.viewmodels.DetailScreenViewModel
import com.example.movieappmad24.viewmodels.MovieViewModelFactory
import kotlinx.coroutines.launch


@Composable
fun DetailScreen(movieId: String?, navController: NavController){ //, moviesViewModel: MoviesViewModel) {

    val db = MovieDatabase.getDatabase(LocalContext.current)
    val repository = MovieRepository(movieDao = db.movieDao())
    val factory = MovieViewModelFactory(repository=repository)
    val viewModel : DetailScreenViewModel = viewModel(factory = factory)

    Scaffold(
        topBar = {
            SimpleTopAppBar(
                topBarText = "${getMovies().find { it.id.equals(movieId)}?.title}",
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
        val moviesState by viewModel.movieList.collectAsState()
        val coroutineScope = rememberCoroutineScope()

        moviesState.find { it.movie.dbId.equals(movieId) }?.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(values),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    MovieCard(
                        movie = it.movie,
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
                    PosterHorizontalScroll(movie = it.movie, sizeDp = 250)
                }
            }
        }
    }
}
