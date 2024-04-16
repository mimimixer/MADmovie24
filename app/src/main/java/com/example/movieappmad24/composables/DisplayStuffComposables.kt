package com.example.movieappmad24.composables

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieappmad24.R
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MoviesViewModel
import com.example.movieappmad24.models.Screen

@Composable
fun MovieList (
    values: PaddingValues,
    movies: List<Movie>,
    navController: NavController
){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(values)
    ) {
        items(movies) { movie ->
            MovieCard(
                movie,
                onItemClick = { movieId -> //this is what is called MovieRow in the exercises
                navController.navigate(route = "${Screen.Detail.route}/$movieId")},
                onFavouriteClick = {movie ->
                    movie.isFavourite = !movie.isFavourite}
            )
        }
    }
}

@Composable
fun PosterHorizontalScroll(movie: Movie, sizeDp: Int){ //, contentScaleVar: ContentScale) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(sizeDp.dp)
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        items(movie.images) { image ->
            /*MovieCard(
                movie = movie,
                number = number,
                onItemClick = {
                    movieId -> Log.d("MovieList", "My callback value: $movieId")
                }
            )*/
            Card(modifier = Modifier
                .size(sizeDp.dp, sizeDp.dp)
                .padding(5.dp)
                .graphicsLayer {
                    clip = true
                    shape = RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp,
                        bottomStart = 20.dp,
                        bottomEnd = 20.dp
                    )
                    shadowElevation = 2.2f
                }
            ) {
                GetMoviePoster(image) //, contentScaleVar)
            }
        }
    }
}

@Composable
fun GetMoviePoster (movie: String){ //, contentScaleVar: ContentScale
    /*if (number != 0){
        poster =movie.images.random()
    }*/
    AsyncImage(
        ImageRequest.Builder(LocalContext.current)
            .data(movie)
            .crossfade(true)
            .build(),
        //for if you want only an image from a specific index:
        // model =  movie.images[1],
        placeholder = painterResource(id = R.drawable.movie_image),
        contentScale = ContentScale.Crop,//contentScaleVar,
        contentDescription = "movie_image",
        modifier = Modifier
            .fillMaxSize()
    )
}

@Composable
//this is what is called MovieRow in the exercises, but I got confused by that name
fun MovieCard(
    movie: Movie,
    onItemClick: (String) -> Unit={},
    onFavouriteClick: (Movie) -> Unit={}
){
    var clickedArrow by remember {
        mutableStateOf(false)
    }
/*    val heartyToClick by rememberSaveable {
        mutableStateOf(true)
    }*/
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .clickable {
                onItemClick(movie.id)
            },
        shape = MaterialTheme.shapes.large, //rounded corner
        elevation = CardDefaults.cardElevation(40.dp)
    ) {
        var picsize: Int
        if(LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT){
            picsize = 150
        } else {
            picsize=300
        }
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    //.aspectRatio(16f / 6f),
                    .height(picsize.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                GetMoviePoster(movie = movie.images[0]) //, contentScaleVar = ContentScale.Crop)
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .clickable {
                            onFavouriteClick(movie)
                                   },
                    contentAlignment = Alignment.TopEnd,
                ) {
                    LikeMoviesHeart(movie.isFavourite)
                }
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = movie.title)
                Box(modifier = Modifier.clickable { clickedArrow = !clickedArrow }) {
                    ToggleArrow(clickedArrow)
                }
            }
        }
        GetMovieInfos(movie = movie, clickedArrow = clickedArrow)
    }
}

@Composable
fun GetMovieInfos (movie: Movie, clickedArrow: Boolean){
    AnimatedVisibility(clickedArrow) {
        Column(
            modifier = Modifier
                .padding(15.dp)
        ) {
            Text(text = "Director: ${movie.director}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Released: ${movie.year}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Genre: ${movie.genre}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Actors: ${movie.actors}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Rating: ${movie.rating}", style = MaterialTheme.typography.bodySmall)
            Divider()
            Text(text = "Plot: ${movie.plot}")
        }
    }
}