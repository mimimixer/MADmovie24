package com.example.movieappmad24

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BadgedBox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.bottomIcons
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import com.example.movieappmad24.ui.theme.Pink80
import com.example.movieappmad24.ui.theme.Purple40


class MainActivity : ComponentActivity() {
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
                        topBar = {
                            MovieTopAppBar()
                        },
                        bottomBar = {
                            MovieBottomNavigationBar()
                        }
                    ) { values -> // 10.3.24 idea of a box from von https://stackoverflow.com/questions/67080426/compose-appbar-overlaps-activity-content
                        Box (modifier = Modifier.padding(values)){
                            MovieList(movies = getMovies(), 1)
                        }

                    }
                }
            }
        }
    }
}
@Composable
fun MovieList (movies: List<Movie>, number: Int){
    LazyColumn(modifier = Modifier
        .fillMaxSize()
    ){
        items(movies){movie ->
            MovieCard(movie, number)
        }
    }
}

//creates a simple TopAppBar with centered title
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieTopAppBar() {
    CenterAlignedTopAppBar(
        title = {
            Text("Movie App", color = Purple40)
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Pink80)
    )
}

//creates a bottomBar with navigational buttons from bottomList
@Composable
fun MovieBottomNavigationBar() {
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    NavigationBar (modifier = Modifier
        //10-3-24 finally found how to round corners
        //https://stackoverflow.com/questions/72270597/bottom-nav-bar-with-curved-edge-and-shadow-jetpack-compose
        .graphicsLayer {
            clip = true
            shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
            shadowElevation = 2.2f
        }
    ){
        bottomIcons.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index 
                          },
                icon = {
                    BadgedBox(badge = {}) {
                        Icon(
                            imageVector = if (index == selectedItemIndex){
                                item.selectedIcon
                            } else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    } 
                },
                label = {
                    Text(text = item.title)
                }
            )
        }
    }
}

@Composable
fun MovieCard(movie: Movie, number: Int){
    var clickedArrow by remember {
        mutableStateOf(false)
    }
    val heartyToClick by remember {
        mutableStateOf(true)
    }
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
    Column{
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            ) {
            GetMoviePoster(movie = movie, number)
            Box(modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
                contentAlignment = Alignment.TopEnd,
                ) {
                LikeMoviesHeart(heartyToClick)
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = movie.title)
            Icon(modifier = Modifier.clickable { clickedArrow = !clickedArrow },
                imageVector = if (clickedArrow) Icons.Outlined.KeyboardArrowDown
                else Icons.Outlined.KeyboardArrowUp,
               // imageVector = Icons.Outlined.KeyboardArrowUp,
                contentDescription = "little arrow toggle button",
            )
        }
    }
            GetMovieInfos(movie = movie, clickedArrow = clickedArrow)
    }
}

@Composable
fun LikeMoviesHeart (hearty: Boolean){
    var heartyToClick by remember {
        mutableStateOf(hearty)
    }
    if (heartyToClick) {
        Icon(
            modifier = Modifier
                .clickable { heartyToClick = !heartyToClick },
            imageVector = Icons.Outlined.FavoriteBorder,
            contentDescription = "little empty heart"
        )
    } else {
        Icon(
            modifier = Modifier
                .clickable { heartyToClick = !heartyToClick },
            imageVector = Icons.Outlined.Favorite,
            tint = Color.Red,
            contentDescription = "little red heart",
        )
    }
}

@Composable
fun GetMovieInfos (movie:Movie, clickedArrow: Boolean){
    AnimatedVisibility (clickedArrow) {
        Column (modifier = Modifier
            .padding(15.dp)
        ) {
            Text(text = "Director: ${movie.director}")
            Text(text = "Released: ${movie.year}")
            Text(text = "Genre: ${movie.genre}")
            Text(text = "Actors: ${movie.actors}")
            Text(text = "Rating: ${movie.rating}")
            Divider()
            Text(text = "Plot: ${movie.plot}")
        }
    }
}

@Composable
fun GetMoviePoster (movie: Movie, number: Int){
    var poster = movie.images[1]
    if (number != 1){
        poster =movie.images.random()
    }
    AsyncImage(
        ImageRequest.Builder(LocalContext.current)
            .data(poster)
            .crossfade(true)
            .build(),
        //for if you want only an image from a specific index:
        // model =  movie.images[1],
        placeholder = painterResource(id = R.drawable.movie_image),
        contentScale = ContentScale.Crop,
        contentDescription = "movie_image"
    )
}

@Preview
@Composable
fun DefaultPreview(){
    MovieAppMAD24Theme {
        MovieList(movies = getMovies(),2)
    }
}