package com.example.movieappmad24.data;

import androidx.room.Query
import com.example.movieappmad24.models.ListOfAllMovies
import com.example.movieappmad24.models.Movie;
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MovieRepository (private val movieDao: MovieDao) : MovieRepositoryInterface {
    override suspend fun addMovie(movie: Movie) = movieDao.insertMovie(movie)
    override suspend fun addMovieImage(movieImage: MovieImage) = movieDao.insertMovieImage(movieImage)

    override suspend fun updateMovie (movie: Movie) = movieDao.updateMovie(movie)
    override suspend fun updateMovieImage (movieImage: MovieImage) = movieDao.updateMovieImage(movieImage)

    override suspend fun deleteMovie (movie: Movie) = movieDao.deleteMovie(movie)
    override suspend fun deleteMovieImage (movieImage: MovieImage) = movieDao.deleteMovieImage(movieImage)

    override fun getAllMoviesWithImages (): Flow<List<MovieWithImages>> = movieDao.getAllMoviesWithImages()
    override fun getFavoriteMoviesWithImages (): Flow<List<MovieWithImages>> = movieDao.getAllFavouriteMoviesWithImages()
    override fun getMovieWithImagesByDBId(dbid: Long): Flow<List<MovieWithImages>> = movieDao.getMovieWithImagesByDBId(dbid)
    override fun getMovieWithImagesById (id: String): Flow<List<MovieWithImages>> = movieDao.getMovieWithImagesById(id)
    override fun getMovieWithImagesByTitle (title: String): Flow<List<MovieWithImages?>> = movieDao.getMovieWithImagesByTitle(title)
    //override fun deleteDatabase(): Unit = movieDao.deleteDatabase()
    //fun getAllMovies(): Flow<List <Movie>> = movieDao.getAllMovies()
    companion object{
        @Volatile
        private var Instance: MovieRepository? = null
        fun getRepositoryInstance(dao: MovieDao) = Instance?: synchronized(this){
            Instance ?: MovieRepository(dao)
                .also { Instance = it }
        }
    }
}

