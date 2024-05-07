package com.example.movieappmad24.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.models.MovieWithImages
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
    fun getMovieByTitle (title: String): Flow<List<Movie>>
    @Query("SELECT * FROM movie")
    //fun getAllMovies(): List <Movie>
    fun getAllMovies(): Flow<List <Movie>>
    @Query("SELECT * FROM movie WHERE isFavourite = true")
    //fun getAllFavouriteMovies(): List< Movie>
    fun getAllFavouriteMovies(): Flow<List< Movie>>

    @Insert
    suspend fun insertMovieImage (movieImage: MovieImage)
    @Update
    suspend fun updateMovieImage (movieImage: MovieImage)
    @Delete
    suspend fun deleteMovieImage (movieImage: MovieImage)
    @Query("SELECT * FROM movieImage WHERE movieDBid = :id")
    fun getMovieImagesToMovieByID (id: String): Flow<List<MovieImage>>
/*
    @Insert
    @Transaction
    suspend fun insertMovieWithImage (movieWithImages: MovieWithImages)
    @Update
    @Transaction
    suspend fun updateMoviewithImage (movieWithImages: MovieWithImages)
    @Delete
    @Transaction
    suspend fun deleteMovieWithImage (movieWithImages: MovieWithImages)
 */
    @Transaction
    @Query("SELECT * FROM Movie WHERE dbId = :dbid")
    fun getMovieWithImagesByDBId (dbid: Long): Flow<List<MovieWithImages>>
    @Transaction
    @Query("SELECT * FROM Movie WHERE id = :id")
    fun getMovieWithImagesById (id: String): Flow<List<MovieWithImages>>

    @Transaction
    @Query("SELECT * FROM Movie WHERE  title = :title")
    fun getMovieWithImagesByTitle (title: String): Flow<List<MovieWithImages?>>

    @Transaction
    @Query("SELECT * FROM Movie")
    fun getAllMoviesWithImages(): Flow<List <MovieWithImages>>
    @Transaction
    @Query("SELECT * FROM Movie WHERE isFavourite = true")
    fun getAllFavouriteMoviesWithImages(): Flow<List< MovieWithImages>>

    //@Transaction
    //@Query("DROP TABLE movie_db")
    //fun deleteDatabase(): Unit
}