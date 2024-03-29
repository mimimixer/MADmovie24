package com.example.movieappmad24.composables

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.movieappmad24.R
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.Screen
import com.example.movieappmad24.models.bottomNavigationIcons
import com.example.movieappmad24.models.deleteMovie
import com.example.movieappmad24.models.setMovies

@Composable
fun MovieList (
    values: PaddingValues,
    movies: List<Movie>,
    navController: NavController){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(values)
    ) {
        items(movies) {
                movie ->
            /*MovieCard(
                movie = movie,
                number = number,
                onItemClick = {
                    movieId -> Log.d("MovieList", "My callback value: $movieId")
                }
            )*/
            MovieCard(movie) {movieId -> //this is what is called MovieRow in the exercises
                navController.navigate(route = "${Screen.Detail.route}/$movieId")
            }
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
        contentScale = ContentScale.Crop, //contentScaleVar,
        contentDescription = "movie_image",
        /* loading = {
             CircularProgressIndicator()
         }*/
    )
}

//creates a simple TopAppBar with centered title
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleTopAppBar(topBarText: String, backArrow: Boolean, navController: NavController) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                if (backArrow) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        modifier = Modifier
                            .clickable {
                                navController.popBackStack()
                            },
                        contentDescription = "navigate Back"
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = topBarText)
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun SimpleBottomAppBar(navController: NavController) {
    BottomNavigation(modifier = Modifier
        //10-3-24 finally found how to round corners
        //https://stackoverflow.com/questions/72270597/bottom-nav-bar-with-curved-edge-and-shadow-jetpack-compose
        .graphicsLayer {
            clip = true
            shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
            shadowElevation = 2.2f
        }
    ) {// 24.03.29 https://developer.android.com/develop/ui/compose/navigation#bottom-nav
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        bottomNavigationIcons.forEach { item ->
            BottomNavigationItem(
                selected = currentDestination?.hierarchy?.any {
                    it.route == item.route
                } == true,
                onClick = {
                    navController.navigate(route = item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id){
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        //imageVector = if (navController.currentDestination?.toString()?.contains(item.route) == true) {
                        item.selectedIcon,
                        // } else {item.unselectedIcon},
                        contentDescription = item.title
                    )
                },
                label = {
                    Text(text = item.title)
                }
            )
        }
    }
}

@Composable
//this is what is called MovieRow in the exercises, but I got confused by that name
fun MovieCard(movie: Movie, onItemClick: (String) -> Unit={}){
    var clickedArrow by remember {
        mutableStateOf(false)
    }
    val heartyToClick by remember {
        mutableStateOf(true)
    }
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
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            ) {
                GetMoviePoster(movie = movie.images[0]) //, contentScaleVar = ContentScale.Crop)
                Box(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd,
                ) {
                    LikeMoviesHeart(heartyToClick, movie)
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
fun LikeMoviesHeart (hearty: Boolean, movie: Movie){
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
        deleteMovie(movie.id)
    } else {
        Icon(
            modifier = Modifier
                .clickable { heartyToClick = !heartyToClick },
            imageVector = Icons.Outlined.Favorite,
            tint = Color.Red,
            contentDescription = "little red heart",

            )
        setMovies(movie.id)
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
            HorizontalDivider()
            Text(text = "Plot: ${movie.plot}")
        }
    }
}

