package com.example.movieappmad24.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class DetailScreenViewModel (repository: MovieRepository, movieID: String): MoviesViewModel(repository, movieID) {


        //private val _movieList = MutableStateFlow(listOf<MovieWithImages>())  //private, so not accessible from
        // outside the viewModel
        //val movieList: StateFlow<List<Movie>> = _movieList.asStateFlow() //accessible, but not mutable

        init {
            viewModelScope.launch {
                println("detalviewmodel")
                repository.getMovieWithImagesById(movieID).distinctUntilChanged()
                    .collect { listOfMovies ->          //susspend function call!
                    movieList.value = listOfMovies
                    }
            }
        }
    val movieListFlow: StateFlow<List<MovieWithImages>> = movieList.asStateFlow() //accessible, but not mutable


    suspend fun setCurrentPosition(movie: Movie, position: Long) {
        movie.playerPositionWhenStops = position
        repository.updateMovie(movie)
    }


    suspend fun togglePlayer (movie: Movie, plays: Boolean) {
        if(plays) {
            movie.playerIsPlaying = true
        }else{
            movie.playerIsPlaying = false
        }
        repository.updateMovie(movie)
    }
}