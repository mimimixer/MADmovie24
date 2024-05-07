package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieappmad24.data.MovieRepository

class MovieViewModelFactory (
    private val repository: MovieRepository, val movieID: String?) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailScreenViewModel::class.java)){
            if (movieID != null) {
                return DetailScreenViewModel(repository = repository, movieID) as T
            }
        }
        if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)){
            return HomeScreenViewModel(repository=repository, movieID) as T
        }
        if (modelClass.isAssignableFrom(WatchlistScreenViewModel::class.java)){
            return WatchlistScreenViewModel(repository=repository, movieID) as T
        }
        throw IllegalArgumentException ("Unknown ViewModel class!")
    }
}