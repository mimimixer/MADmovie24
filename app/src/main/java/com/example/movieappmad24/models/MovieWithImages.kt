package com.example.movieappmad24.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
@Entity
data class MovieWithImages(
    @PrimaryKey(autoGenerate = true) val dbId: Long = 0,
    @ColumnInfo val id: String,
    @ColumnInfo val title: String,
    @Ignore val images: List<String>,
)
