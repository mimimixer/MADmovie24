package com.example.movieappmad24.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieImage
import kotlinx.coroutines.flow.Flow

interface MovieImageDao {

    @Insert
    suspend fun insertMovieImage (movieImage: MovieImage)
    @Update
    suspend fun updateMovieImage (movieImage: MovieImage)
    @Delete
    suspend fun deleteMovieImage (movieImage: MovieImage)
    @Query("SELECT * FROM movieImage WHERE imageId = :id")
    fun getSingleMovieImage (id: Long): Flow<MovieImage>

    @Query("SELECT * FROM movieImage WHERE movieID = :id")
    fun getMovieImagesToMovieByID (id: String): Flow<List<MovieImage>>

    @Query("SELECT * FROM movieImage WHERE  movieTitle = :id")
    fun getMovieImagesToMovieByTitle (id: String): Flow<List<MovieImage>>

    @Query("SELECT * FROM movieImage")
    fun getAllMovieImages(): Flow<List<MovieImage>>

}