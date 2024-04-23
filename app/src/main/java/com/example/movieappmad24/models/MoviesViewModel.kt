package com.example.movieappmad24.models


import androidx.compose.runtime.remember
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.media3.exoplayer.ExoPlayer

// Inherit from ViewModel class
class MoviesViewModel : ViewModel() {

    private val _movieList = getMovies().toMutableList()

    // get all movies and create a StateHolder from it, so it can be observed by UI
    val movieList: List<Movie> // expose previously created list but immutable
        get() = _movieList

    // rest of logic
    val watchList: List<Movie> // expose previously created list but immutable
        get() = _movieList.filter { movie -> movie.isFavourite}

    val noWatchList = getDefaultMovies()
    fun toggleFavoriteMovie(movieId: String) = _movieList.find { it.id == movieId }?.let { movie ->
        movie.isFavourite = movie.isFavourite
    }

    fun setCurrentPosition(movie: Movie, position: Long) = _movieList.find { it == movie }?.let { movie ->
        movie.playerReset = position
    }


    fun togglePlayer (movie: Movie, plays: Boolean) = _movieList.find { it == movie }?.let { movie ->
        if(plays) {
            movie.playerPlays = true
        }else{
            movie.playerPlays = false
        }
    }

}