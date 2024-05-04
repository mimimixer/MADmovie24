package com.example.movieappmad24.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getDefaultMovies
import com.example.movieappmad24.models.getMovies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

// Inherit from ViewModel class
abstract class MoviesViewModel (val repository : MovieRepository): ViewModel() {

    // val repository : MovieRepository = TODO()

    private val _movieList = MutableStateFlow(listOf<Movie>())  //private, so not accessible from
                                                            // outside the viewModel
    val movieList: StateFlow<List<Movie>> = _movieList.asStateFlow() //accessible, but not mutable
/*
    init {
        viewModelScope.launch {
            repository.getAllMovies().distinctUntilChanged()
                .collect { listOfMovies ->          //susspend function call!
                    _movies.value = listOfMovies
                }
        }
    }

*/
    //private val _movieList = getMovies().toMutableList()

    // get all movies and create a StateHolder from it, so it can be observed by UI
    //val movieList: List<Movie> // expose previously created list but immutable
        //get() = _movieList

    // rest of logic
    // val watchList: List<Movie> // expose previously created list but immutable
        //get() = _movieList.filter { movie -> movie.wasFavourited}

    val noWatchList = getDefaultMovies()
    suspend fun toggleFavoriteMovie(movie: Movie) {
        movie.isFavourite = !movie.isFavourite
        repository.updateMovie(movie)
    }


}

