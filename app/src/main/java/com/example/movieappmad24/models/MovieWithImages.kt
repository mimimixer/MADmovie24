package com.example.movieappmad24.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

data class MovieWithImages(
    @Embedded val movie: Movie,
    @Relation(
        parentColumn = "dbId",
        entityColumn = "movieDBid"
    )
        val movieImages: List<MovieImage>
)
