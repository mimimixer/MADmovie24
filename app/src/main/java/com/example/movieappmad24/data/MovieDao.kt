package com.example.movieappmad24.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieImage
import kotlinx.coroutines.flow.Flow
@Dao
interface MovieDao {
    @Insert
    suspend fun insertMovie (movie: Movie)
    @Update
    suspend fun updateMovie (movie: Movie)
    @Delete
    suspend fun deleteMovie (movie: Movie)
    @Query("SELECT * FROM movie WHERE dbId = :id")
    // fun getMovieById (id: Long): Movie
    fun getMovieById (id: Long): Flow<Movie>

    @Query("SELECT * FROM movie WHERE  title = :title")
    fun getMovieByTitle (title: String): Flow<List<MovieImage>>

    @Query("SELECT * FROM movie")
    //fun getAllMovies(): List <Movie>
    fun getAllMovies(): Flow<List <Movie>>

    @Query("SELECT * FROM movie WHERE isFavourite = true")
    //fun getAllFavouriteMovies(): List< Movie>
    fun getAllFavouriteMovies(): Flow<List< Movie>>
}