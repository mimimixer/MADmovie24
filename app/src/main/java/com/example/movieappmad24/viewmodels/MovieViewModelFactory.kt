package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieappmad24.data.MovieRepository

class MovieViewModelFactory (
    private val repository: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailScreenViewModel::class.java)){
            return DetailScreenViewModel(repository=repository) as T
        }
        if (modelClass.isAssignableFrom(HomeScreenViewModel::class.java)){
            return HomeScreenViewModel(repository=repository) as T
        }
        if (modelClass.isAssignableFrom(WatchlistScreenViewModel::class.java)){
            return WatchlistScreenViewModel(repository=repository) as T
        }
        throw IllegalArgumentException ("Unknown ViewModel class!")
    }
}