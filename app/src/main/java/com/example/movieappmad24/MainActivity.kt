package com.example.movieappmad24

import android.graphics.Paint.Align
import android.media.ImageReader
import android.os.Bundle
import android.widget.GridLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppMAD24Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize(),
                        topBar = {
                            MovieTopAppBar()
                        },
                        bottomBar = {
                            MovieBottomAppBar()
                        }
                    ) { values ->
                        MovieList(movies = allMovieNames, modifier = Modifier.padding(values))

                    }
                }
            }
        }
    }
}
@Composable
fun MovieList (movies: List<Movie>, modifier: Modifier){
    LazyColumn(modifier = Modifier
        .fillMaxSize()
    ){
        items(movies){movie ->
            MovieRow(movie)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTopAppBar() {
    CenterAlignedTopAppBar(
        title = {
            Text("Movie App")
        }
    )
}
@Composable
fun MovieBottomAppBar() {

            BottomAppBar(modifier = Modifier
                .padding(0.dp)
                .height(100.dp)) {
                Row(
                    Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clip(CircleShape)) {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
                        }
                        Text(text = "Home", color = Color.Black)
                    }
                    Column {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.Star, contentDescription = "Watchlist")
                        }
                        Text(text = "Watchlist", color = Color.Black)
                    }

                }
            }

}


@Composable
fun MovieRow(movie: Movie){
    var clickedArrow by remember { mutableStateOf(false) }
    Card {
    Column(modifier = Modifier
        .padding(Dp(12F))) {
        Box(modifier = Modifier
            .padding(0.dp)
            .height(150.dp)) {
            AsyncImage(
                model = movie.images.random(),
                //ImageRequest.Builder(LocalContext.current)
                  //  .data({movie.images.random()})
                    //.crossfade(true)
                    //.build(),
                placeholder = painterResource(id = R.drawable.movie_image),
                contentScale = ContentScale.Crop,
                contentDescription = "movie_image"
            )
            Box(modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd) {
                Icon(
                    painter = painterResource(id = R.drawable.outline_favorite_border_24),
                    contentDescription = "little_heart"
                )
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .background(color = Color.White),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = movie.title, color = Color.Black)
            Icon(modifier = Modifier.clickable { clickedArrow = !clickedArrow },
                imageVector = if (clickedArrow) Icons.Outlined.KeyboardArrowDown
                else Icons.Outlined.KeyboardArrowUp,
               // imageVector = Icons.Outlined.KeyboardArrowUp,
                contentDescription = "little arrow toggle button",
                tint = Color.Black
            )
        }
    }
        AnimatedVisibility (clickedArrow) {
            Column {
                Text(text = "Director: ${movie.director}", color = Color.Black)
                Text(text = "Released: ${movie.year}", color = Color.Black)
                Text(text = "Genre: ${movie.genre}", color = Color.Black)
                Text(text = "Actors: ${movie.actors}", color = Color.Black)
                Text(text = "Rating: ${movie.rating}", color = Color.Black)
                Divider()
                Text(text = "Plot: ${movie.plot}", color = Color.Black)
            }

        }
    }
}


@Preview
@Composable
fun DefaultPreview(){
    MovieAppMAD24Theme {
        MovieList(movies = allMovieNames, modifier = Modifier)
    }
}


var allMovieNames = getMovies()

    //listOf("Avatar",  "Blow-Up", "Casablanca", "Dune", "eXistenZ", "Freaks" )