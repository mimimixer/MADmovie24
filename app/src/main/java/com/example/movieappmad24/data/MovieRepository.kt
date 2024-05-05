package com.example.movieappmad24.data;

import com.example.movieappmad24.models.ListOfAllMovies
import com.example.movieappmad24.models.Movie;
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.Flow

class MovieRepository (private val movieDao: MovieDao){
    fun insertAllMovies() = movieDao.insertMovieList(ListOfAllMovies)
    suspend fun addMovies(movie: Movie) = movieDao.insertMovie(movie)
    suspend fun addMovieImage(movieImage: MovieImage) = movieDao.insertMovieImage(movieImage)

    suspend fun updateMovie (movie: Movie) = movieDao.updateMovie(movie)
    suspend fun updateMovieImage (movieImage: MovieImage) = movieDao.updateMovieImage(movieImage)

    suspend fun deleteMovie (movie: Movie) = movieDao.deleteMovie(movie)
    suspend fun deleteMovieImage (movieImage: MovieImage) = movieDao.deleteMovieImage(movieImage)

    fun getAllMoviesWithImages (): Flow<List<MovieWithImages>> = movieDao.getAllMoviesWithImages()
    fun getFavoriteMoviesWithImages (): Flow<List<MovieWithImages>> = movieDao.getAllFavouriteMoviesWithImages()
    fun getMovieWithImagesById (id: Long): Flow<List<MovieWithImages?>> = movieDao.getMovieWithImagesById(id)
    fun getMovieWithImagesByTitle (title: String): Flow<List<MovieWithImages?>> = movieDao.getMovieWithImagesByTitle(title)

}

