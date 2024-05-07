package com.example.movieappmad24.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class HomeScreenViewModel (repository: MovieRepository, movieID: String?): MoviesViewModel(repository, movieID) {

    //private val _movieList = MutableStateFlow(listOf<MovieWithImages>())  //private, so not accessible from
    // outside the viewModel
    //val movieList: StateFlow<List<Movie>> = _movieList.asStateFlow() //accessible, but not mutable
    init {
        viewModelScope.launch {
            println("homeviewmodel")
            repository.getAllMoviesWithImages().distinctUntilChanged()
                .collect { listOfMovies ->          //suspend function call!
                    movieList.value = listOfMovies
                    //println("original list of movies is ${getMovies()}")
                    //println("got movies for homescreen: ${listOfMovies}")
                }
        }
    }
    val movieListFlow: StateFlow<List<MovieWithImages>> = movieList.asStateFlow() //accessible, but not mutable

}