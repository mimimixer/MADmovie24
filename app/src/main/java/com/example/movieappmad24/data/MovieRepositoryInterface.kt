package com.example.movieappmad24.data

import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.Flow

interface MovieRepositoryInterface {
        suspend fun addMovies(movie: Movie)
        suspend fun addMovieImage(movieImage: MovieImage)

        suspend fun updateMovie (movie: Movie)
        suspend fun updateMovieImage (movieImage: MovieImage)

        suspend fun deleteMovie (movie: Movie)
        suspend fun deleteMovieImage (movieImage: MovieImage)

        fun getAllMoviesWithImages (): Flow<List<MovieWithImages>>
        fun getFavoriteMoviesWithImages (): Flow<List<MovieWithImages>>
        fun getMovieWithImagesById (id: Long): Flow<List<MovieWithImages?>>
        fun getMovieWithImagesByTitle (title: String): Flow<List<MovieWithImages?>>
}