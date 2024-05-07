package com.example.movieappmad24.data

import android.app.Notification
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.movieappmad24.R
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.createMovieImage
import com.example.movieappmad24.models.getMovies

private const val TAG = "SeedMovieDatabaseWorker"
class SeedMovieDatabaseWorker(private val context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        makeStatusNotification(
            applicationContext.resources.getString(R.string.seed_worker),
            applicationContext
        )
        return try {
            val movieDao = MovieDatabase.getDatabase(context).movieDao()
            val repo = MovieRepository(movieDao)
            val movies = getMovies()
            movies.forEach(){movie: Movie ->
                repo.addMovies(movie)
                //println("movie dbid# ${movie.dbId} and title ${movie.title} added")
               /* movie.images.forEach(){it ->
                    val movieImage = createMovieImage(movie, it)
                    repo.addMovieImage(movieImage)
                    println("image for movie dbid# ${movie.dbId} and title ${movie.title} added")
                }*/
            }
            /*
            movies.forEach(){movie: Movie ->
                repo.addMovies(movie)
                println("movie dbid# ${movie.dbId} and title ${movie.title} added")
                movie.images.forEach(){it ->
                    val movieImage = createMovieImage(movie, it)
                    repo.addMovieImage(movieImage)
                    println("image for movie dbid# ${movie.dbId} and title ${movie.title} added")
                }
            }*/
            Result.success()
        } catch (throwable: Throwable) {
            Log.e(TAG, applicationContext.resources.getString(R.string.seed_worker), throwable)
            Result.failure()
        }
    }

    private fun makeStatusNotification(string: String, applicationContext: Context) {
        Notification()
    }
    /*
    var NOTIFICATION_ID = 0
    override suspend fun getForegroundInfo(): ForegroundInfo {

        return ForegroundInfo(
            NOTIFICATION_ID, createNotification()
        )
    }
    override suspend fun doWork(): Result {
        val movieDao = MovieDatabase.getDatabase(context).movieDao()
        val repo = MovieRepository(movieDao)
        // Insert initial data into the database using movieDao
        // For example:
        val movies = getMovies()
        movies.forEach(){movie: Movie ->
            repo.addMovies(movie)
            println("movie dbid# ${movie.dbId} and title ${movie.title} added")
            movie.images.forEach(){it ->
                val movieImage = createMovieImage(movie, it)
                repo.addMovieImage(movieImage)
                println("image for movie dbid# ${movie.dbId} and title ${movie.title} added")

            }
        }
        return Result.success()

       /* val seedDatabaseWorkRequest: WorkRequest =
            OneTimeWorkRequestBuilder<SeedMovieDatabaseWorker>()
                .build()*/
    }
    private fun createNotification() : Notification {
        NOTIFICATION_ID ++
        return Notification()
    }

     */

}

