package com.example.movieappmad24.data;

import com.example.movieappmad24.models.ListOfAllMovies
import com.example.movieappmad24.models.Movie;
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.Flow

class MovieRepository (private val movieDao: MovieDao) : MovieRepositoryInterface {
    override suspend fun addMovies(movie: Movie) = movieDao.insertMovie(movie)
    override suspend fun addMovieImage(movieImage: MovieImage) = movieDao.insertMovieImage(movieImage)

    override suspend fun updateMovie (movie: Movie) = movieDao.updateMovie(movie)
    override suspend fun updateMovieImage (movieImage: MovieImage) = movieDao.updateMovieImage(movieImage)

    override suspend fun deleteMovie (movie: Movie) = movieDao.deleteMovie(movie)
    override suspend fun deleteMovieImage (movieImage: MovieImage) = movieDao.deleteMovieImage(movieImage)

    override fun getAllMoviesWithImages (): Flow<List<MovieWithImages>> = movieDao.getAllMoviesWithImages()
    override fun getFavoriteMoviesWithImages (): Flow<List<MovieWithImages>> = movieDao.getAllFavouriteMoviesWithImages()
    override fun getMovieWithImagesById (id: Long): Flow<List<MovieWithImages?>> = movieDao.getMovieWithImagesById(id)
    override fun getMovieWithImagesByTitle (title: String): Flow<List<MovieWithImages?>> = movieDao.getMovieWithImagesByTitle(title)

    //fun getAllMovies(): Flow<List <Movie>> = movieDao.getAllMovies()
    companion object{
        @Volatile
        private var Instance: MovieRepository? = null
        fun getRepositoryInstance(dao: MovieDao) = Instance?: synchronized(this){
            Instance ?: MovieRepository(dao).also { Instance = it }
        }
    }
}

