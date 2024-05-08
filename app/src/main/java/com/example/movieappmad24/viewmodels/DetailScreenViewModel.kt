package com.example.movieappmad24.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class DetailScreenViewModel (repository: MovieRepository, movieID: String): MoviesViewModel(repository, movieID) {


        //private val _movieList = MutableStateFlow(listOf<MovieWithImages>())  //private, so not accessible from
        // outside the viewModel
        //val movieList: StateFlow<List<Movie>> = _movieList.asStateFlow() //accessible, but not mutable
        val movieListFlow: StateFlow<List<MovieWithImages>> = movieList.asStateFlow() //accessible, but not mutable

        init {
            viewModelScope.launch {
                println("detailviewmodel reinitialized")
                repository.getMovieWithImagesById(movieID).distinctUntilChanged()
                    .collect { listOfMovies ->          //suspend function call!
                    movieList.value = listOfMovies
                    }
                playerPositionOfDetailScreen = 0
                detailScreenPlayerIsPlaying = false
            }
        }

    var playerPositionOfDetailScreen by mutableLongStateOf(0)
    var detailScreenPlayerIsPlaying by mutableStateOf(false)

  /*  fun setCurrentPosition(movie: Movie, position: Long) {
        //movie.playerReset = position
        playerPositionOfDetailScreen = position
        //repository.updateMovie(movie)
    }


    suspend fun togglePlayer (movie: Movie, plays: Boolean) {
        if(plays) {
            //movie.playerPlays = true
            detailScreenPlayerIsPlaying = true
        }else{
            //movie.playerPlays = false
            detailScreenPlayerIsPlaying = false
        }
        //repository.updateMovie(movie)
    }*/

}