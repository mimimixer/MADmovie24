package com.example.movieappmad24.models

import androidx.compose.material.FabPosition
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.movieappmad24.composables.PlayerTrailer
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
        movie.isFavourite = !movie.isFavourite
    }

    fun setCurrentPosition(movie: Movie, position: Long) = _movieList.find { it == movie }?.let { movie ->
        println("positionRemember was at $position")
        movie.playerReset = position
        println("positionRemember is reset to ${movie.playerReset}")
    }

    fun togglePlayer (movie: Movie, plays: Boolean) = _movieList.find { it == movie }?.let { movie ->
        println("player plays $plays")
        if(plays) {
            movie.playerPlays = true
        }else{
            movie.playerPlays = false
        }
        println("player plays ${movie.playerPlays}")
    }

}