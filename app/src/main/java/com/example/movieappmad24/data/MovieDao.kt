package com.example.movieappmad24.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.movieappmad24.models.Movie

interface MovieDao {
    @Insert
    fun insertMovie (movie: Movie)
    @Update
    fun updateMovie (movie: Movie)
    @Delete
    fun deleteMovie (movie: Movie)
    @Query("SELECT * FROM movie WHERE movie.id LIKE :id")
    fun getMovieById (id: Long, movie: Movie)
    @Query("SELECT * FROM movie")
    fun getAllMovies (movie: Movie)
    @Query("SELECT * FROM movie WHERE movie.favourite = true")
    fun getAllFavouriteMovies (movie: Movie)
}