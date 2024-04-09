package com.example.movieappmad24.models

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies

// Inherit from ViewModel class
class MoviesViewModel : ViewModel() {
    private val _movieList = getMovies().toMutableList()
    // get all movies and create a StateHolder from it, so it can be observed by UI
    val movieList: List<Movie> // expose previously created list but immutable
        get() = _movieList
// rest of logic
val watchList: List<Movie> // expose previously created list but immutable
    get() = _movieList.filter { movie -> movie.isFavourite }

fun toggleFavoriteMovie(movieId: String) = _movieList.find { it.id == movieId }?.let { movie ->
    movie.isFavourite = !movie.isFavourite }
}