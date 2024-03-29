package com.example.movieappmad24

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.movieappmad24.navigation.Navigation
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme


class MainActivity : ComponentActivity() {
    //lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppMAD24Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(navController =  rememberNavController())
                }
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