package com.example.movieappmad24

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.movieappmad24.navigation.Navigation
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme


class MainActivity : ComponentActivity() {
    //lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppMAD24Theme {
                            Navigation(rememberNavController())
            }
        }
    }
}


/*
@Preview
@Composable
fun DefaultPreview(){
    MovieAppMAD24Theme {
        MovieList(movies = getMovies(),0, )
    }
}
*/