package com.example.teldatask.presentation.screens.movies_home_screen.preview_data

import com.example.teldatask.presentation.screens.movies_home_screen.MoviesHomeUiState
import com.example.teldatask.presentation.screens.movies_home_screen.model.MovieUiModel


val fakeMovieUiModel =
    MovieUiModel(
        id = 1,
        title = "The Shawshank Redemption",
        posterPath = "https://www.vintagemovieposters.co.uk/wp-content/uploads/2023/03/IMG_1887-scaled.jpeg",
        releaseDate = "1994-09-23",
        voteAverage = 8.6,
        overview = "Two imprisoned men forge a remarkable bond over the years, forming a system of mutual support and survival.",
        originalLanguage = "en",
        isFavourite = true
    )


val fakePopularMovies = listOf(
    MovieUiModel(
        id = 1,
        title = "The Shawshank Redemption",
        posterPath = "https://image.tmdb.org/t/p/w500/q6y0Go1POy89lB7qAUcKc59FH.jpg",
        releaseDate = "1994-09-23",
        voteAverage = 8.6,
        overview = "Two imprisoned men forge a remarkable bond over the years, forming a system of mutual support and survival.",
        originalLanguage = "en",
        isFavourite = true
    ),
    MovieUiModel(
        id = 2,
        title = "The Godfather",
        posterPath = "https://image.tmdb.org/t/p/w500/1GVnPQcMd5V4s6g553089N5Qp.jpg",
        releaseDate = "1972-03-24",
        voteAverage = 8.5,
        overview = "The Corleone family is one of the most powerful crime families in New York City.",
        originalLanguage = "en",
        isFavourite = false
    ),
)

val fakeMoviesHomeUiState = MoviesHomeUiState.PopularMoviesList(popularMoviesList = fakePopularMovies)
