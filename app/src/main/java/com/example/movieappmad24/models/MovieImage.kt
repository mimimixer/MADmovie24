package com.example.movieappmad24.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class MovieImage(
    @PrimaryKey(autoGenerate = true) val imageId: Long = 0,
    @ColumnInfo val movieDBid: Long,
    @ColumnInfo val movieTitle: String,
    val image: String
)

fun createMovieImage(movie: Movie, movieImage: String) : MovieImage{
    return MovieImage(
            movieDBid = movie.dbId,
            movieTitle = movie.title,
            image = movieImage
        )
}


