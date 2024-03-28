package com.example.movieappmad24.models

data class DefaultWatchlistMovie(
    val id: String,
    val title: String,
    val year: String,
    val genre: String,
    val director: String,
    val actors: String,
    val plot: String,
    val images: List<String>,
    val trailer: String,
    val rating: String
)

fun getDefaultMovies(): MutableList<Movie> {
    return mutableListOf(
        Movie(id = "tt3472226",
            title = "Kung Fury",
            year = "2015",
            genre = "Short, Action, Comedy",
            director = "David Sandberg",
            actors = "David Sandberg, Jorma Taccone, Steven Chew",
            plot = "In 1985, Kung Fury, the toughest martial artist cop in Miami, goes back in time to kill the worst criminal of all time - Kung Führer, a.k.a. Adolf Hitler.",
            images = listOf("https://m.media-amazon.com/images/M/MV5BMjQwMjU2ODU5NF5BMl5BanBnXkFtZTgwNTU1NjM4NTE@._V1_SX300.jpg"),
            trailer = "trailer_placeholder",
            rating = "8.0"),
    Movie(id = "tt0050306",
        title = "Designing Woman",
        year = "1957",
        genre = "Comedy, Romance",
        director = "Comedy, Romance",
        actors = "Lauren Bacall, Gregory Peck, Dolores Gray",
        plot = "A sportswriter and a fashion-designer marry after a whirlwind romance, and discover they have little in common.",
        images = listOf("https://m.media-amazon.com/images/M/MV5BZDAxZjIwZmUtMjhiYi00NmQ0LWE1YmItNTNiODBkYWZiZjgxXkEyXkFqcGdeQXVyMDI2NDg0NQ@@._V1_SX300.jpg"),
        trailer = "trailer_placeholder",
        rating = "6.7")
    )
}