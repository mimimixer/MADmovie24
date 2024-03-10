package com.example.movieappmad24.composables

import android.icu.text.CaseMap.Title
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.BottomAppBar
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.BottomAppBarDefaults
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
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieappmad24.R
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import com.example.movieappmad24.ui.theme.Pink40
import com.example.movieappmad24.ui.theme.Pink80
import com.example.movieappmad24.ui.theme.Purple40
import com.example.movieappmad24.ui.theme.Purple80


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
                            .fillMaxSize()
                            .padding(),
                        topBar = {
                            MovieTopAppBar()
                        },
                        bottomBar = {
                            MovieBottomNavigationBar()
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
            Text("Movie App", color = Purple40)
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Pink80)
    )
}

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

val bottomIcons = listOf(
    BottomNavigationItem (
        title = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Default.Home
    ),
    BottomNavigationItem(
        title = "Watchlist",
        selectedIcon = Icons.Filled.Star,
        unselectedIcon = Icons.Filled.Star
    )
)


@Composable
fun MovieBottomNavigationBar() {
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    NavigationBar (modifier = Modifier
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
fun MovieRow(movie: Movie){
    var clickedArrow by remember {
        mutableStateOf(false)
    }
    var heartyToClick by remember {
        mutableStateOf(true)
    }
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
    Column() {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            ) {
            AsyncImage(
                // model = movie.images.random(),
                ImageRequest.Builder(LocalContext.current)
                    .data(movie.images[1])
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.movie_image),
                contentScale = ContentScale.Crop,
                contentDescription = "movie_image"
            )
            Box(modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
                contentAlignment = Alignment.TopEnd,
                ) {
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
        AnimatedVisibility (clickedArrow) {
            Column (modifier = Modifier
                .padding(15.dp)
            ) {
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