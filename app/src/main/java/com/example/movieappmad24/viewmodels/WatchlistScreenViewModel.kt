package com.example.movieappmad24.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class WatchlistScreenViewModel(repository: MovieRepository): MoviesViewModel(repository) {

    private val _movieList = MutableStateFlow(listOf<MovieWithImages>())  //private, so not accessible from
    // outside the viewModel
    //val movieList: StateFlow<List<Movie>> = _movieList.asStateFlow() //accessible, but not mutable

    init {
        viewModelScope.launch {
            println("watchlistviewmodel")
            repository.getFavoriteMoviesWithImages().distinctUntilChanged()
                .collect { listOfMovies -> //susspend function call!
                    _movieList.value = listOfMovies
                }
        }
    }
}