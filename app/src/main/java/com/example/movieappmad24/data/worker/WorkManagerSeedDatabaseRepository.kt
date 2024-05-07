package com.example.movieappmad24.data.worker

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.movieappmad24.data.worker.SeedDatabaseWorker
import com.example.movieappmad24.data.worker.SeedImageDatabaseWorker
import com.example.movieappmad24.data.worker.SeedMovieDatabaseWorker

class WorkManagerSeedDatabaseRepository (context: Context) {

    private val workManager = WorkManager.getInstance(context)

    fun seedAll(){
        workManager.enqueue(OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build())
    }
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