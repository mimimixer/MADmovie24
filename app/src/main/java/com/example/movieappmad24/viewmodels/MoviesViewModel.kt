package com.example.movieappmad24.viewmodels


import androidx.lifecycle.ViewModel
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.MutableStateFlow

// Inherit from ViewModel class
abstract class MoviesViewModel (val repository : MovieRepository, movieID: String?): ViewModel() {

    // val repository : MovieRepository = TODO()

    val movieList = MutableStateFlow(listOf<MovieWithImages>())  //private, so not accessible from
                                                            // outside the viewModel
    //val movieListFlow: StateFlow<List<MovieWithImages>> = movieList.asStateFlow() //accessible, but not mutable
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

    //val noWatchList = getDefaultMovies()
    open suspend fun toggleFavoriteMovie(movie: Movie) {
        movie.isFavourite = !movie.isFavourite
        repository.updateMovie(movie)
    }


}

