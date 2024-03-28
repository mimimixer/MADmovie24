package com.example.movieappmad24.models
import com.example.movieappmad24.models.getMovies

data class WatchlistMovies (
    val movieId: String
)

var watchlist = mutableListOf<String>()

fun setMovies(movieId: String) : MutableList<String> {
    if (watchlist.contains(movieId)) {
        return watchlist
    }else{
        watchlist.add(movieId)
        return watchlist
    }
}

fun deleteMovie(movieId: String) {
    if (watchlist.isNotEmpty()) {
        for (movieId in watchlist) {
            watchlist.remove(movieId)
        }
    }
}

fun getWatchlistMovies(): MutableList<Movie>? {
    var thisWatchlistmovie : Movie
    var watchlistMovies = mutableListOf<Movie>()
    watchlist.forEach{ item ->
            getMovies().find { it.id == item}?.let {
                thisWatchlistmovie = (
                        Movie(
                    id = it.id,
                    title = it.title,
                    year = it.year,
                    genre = it.genre,
                    director = it.director,
                    actors = it.actors,
                    plot = it.plot,
                    images = it.images,
                    trailer = it.trailer,
                    rating = it.rating
                )
                )
                watchlistMovies.add(thisWatchlistmovie)
            }
            }
    return watchlistMovies
}

