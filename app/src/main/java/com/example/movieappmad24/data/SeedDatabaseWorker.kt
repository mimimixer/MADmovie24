package com.example.movieappmad24.data

import android.app.Notification
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkRequest
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.movieappmad24.models.ListOfAllMovies
import com.example.movieappmad24.models.getMovies

class SeedDatabaseWorker(private val context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    var NOTIFICATION_ID = 0
    override suspend fun getForegroundInfo(): ForegroundInfo {

        return ForegroundInfo(
            NOTIFICATION_ID, createNotification()
        )
    }
    override suspend fun doWork(): Result {
        val movieDao = MovieDatabase.getDatabase(context).movieDao()
        //val repo = MovieRepository(movieDao)
        // Insert initial data into the database using movieDao
        // For example:
        //val movies = getMovies()
        movieDao.insertMovieList(ListOfAllMovies)
        return Result.success()

       /* val seedDatabaseWorkRequest: WorkRequest =
            OneTimeWorkRequestBuilder<SeedDatabaseWorker>()
                .build()*/
    }
    private fun createNotification() : Notification {
        NOTIFICATION_ID ++
        return Notification()
    }
}