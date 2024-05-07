package com.example.movieappmad24.data

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.Flow

class WorkManagerSeedDatabaseRepository (context: Context) {

    private val workManager = WorkManager.getInstance(context)

    fun seedMovie(){
        workManager.enqueue(OneTimeWorkRequestBuilder<SeedMovieDatabaseWorker>().build())
    }
    fun seedImage(){
        workManager.enqueue(OneTimeWorkRequestBuilder<SeedImageDatabaseWorker>().build())
    }
/*
    suspend fun addMovieImage(movieImage: MovieImage) {
        val seedMovieImage = OneTimeWorkRequestBuilder<SeedImageDatabaseWorker>()
        workManager.enqueue(seedMovieImage.build())
    }
 */
}