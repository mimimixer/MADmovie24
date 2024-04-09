package com.example.movieappmad24.composables

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.deleteMovie
import com.example.movieappmad24.models.setMovies

@Composable
fun ToggleArrow (clicked: Boolean){
    if (clicked) {
        Icon(
            imageVector = Icons.Outlined.KeyboardArrowDown,
            contentDescription = "arrow down show"
        )
    } else {
        Icon(
            imageVector = Icons.Outlined.KeyboardArrowUp,
            contentDescription = "arrow up not show",
        )
    }
}

@Composable
fun LikeMoviesHeart (hearty: Boolean){
/*    var heartyToClick by remember {
        mutableStateOf(hearty)
    }*/
    if (!hearty) {
        Icon(
            //modifier = Modifier
              //  .clickable { heartyToClick = !heartyToClick },
            imageVector = Icons.Outlined.FavoriteBorder,
            tint = Color.Yellow,
            contentDescription = "little empty heart"
        )
        //movie.isFavourite = !movie.isFavourite
    } else {
        Icon(
           // modifier = Modifier
             //   .clickable { heartyToClick = !heartyToClick },
            imageVector = Icons.Outlined.Favorite,
            tint = Color.Red,
            contentDescription = "little red heart",
            )
       // movie.isFavourite = !movie.isFavourite
    }
}

