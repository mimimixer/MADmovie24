package com.example.movieappmad24.viewmodels.DependencyInjection

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieappmad24.data.MovieDatabase
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.viewmodels.HomeScreenViewModel
import com.example.movieappmad24.viewmodels.MovieViewModelFactory

object InjectorUtils { //like static class in Java
    //need this to avoid boilerplate code in the screens (Home, Watchlist etc)
    // for calling dao-database-repository-the viewmodels, as viewmodels take repository as parameter
    private fun getMovieRepository(context: Context) :MovieRepository {
        return MovieRepository.getRepositoryInstance(MovieDatabase.getDatabase(context.applicationContext).movieDao())
    }
    fun provideMovieViewModelFactory(context: Context, movieID: String?): MovieViewModelFactory{
        val repository = getMovieRepository(context)
        return MovieViewModelFactory(repository, movieID)
    }
}