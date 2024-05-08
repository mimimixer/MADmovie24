package com.example.movieappmad24.data.worker

import android.app.Notification
import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.movieappmad24.R
import com.example.movieappmad24.data.MovieDatabase
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.ListOfAllMovies
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.createMovieImage
import com.example.movieappmad24.models.getMovies

private const val TAGi = "SeedImageDatabaseWorker"
class SeedImageDatabaseWorker (private val context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

        override suspend fun doWork(): Result {
            makeStatusNotification(
                applicationContext.resources.getString(R.string.seed_image_worker),
                applicationContext
            )
            return try {
                val movieDao = MovieDatabase.getDatabase(context).movieDao()
                val repo = MovieRepository(movieDao)
                val movies = getMovies()
                movies.forEach{movie: Movie ->
                    movie.images.forEach{
                        val movieImage = createMovieImage(movie, it)
                        repo.addMovieImage(movieImage)
                        println("image for movie dbid# ${movie.dbId} and title ${movie.title} added")
                    }
                }
                Result.success()
            } catch (throwable: Throwable) {
                Log.e(TAGi, applicationContext.resources.getString(R.string.seed_image_worker), throwable)
                Result.failure()
            }
        }

        private fun makeStatusNotification(string: String, applicationContext: Context) {
            Notification()
        }
}