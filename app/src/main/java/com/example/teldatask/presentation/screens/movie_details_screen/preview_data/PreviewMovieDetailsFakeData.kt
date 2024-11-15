package com.example.teldatask.presentation.screens.movie_details_screen.preview_data

import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movie_details.Genre
import com.example.teldatask.presentation.model.CustomApiExceptionUiModel
import com.example.teldatask.presentation.screens.movie_details_screen.SimilarMoviesSection
import com.example.teldatask.presentation.screens.movie_details_screen.ui_state.MovieDetailsFirstSectionUiState
import com.example.teldatask.presentation.screens.movie_details_screen.model.MovieDetailsUiModel
import com.example.teldatask.presentation.screens.movie_details_screen.ui_state.SimilarMoviesUiState
import com.example.teldatask.presentation.screens.movies_home_screen.model.MovieUiModel

val fakeMovieDetailsUiModel = MovieDetailsUiModel(
    id = 1,
    image = "https://image.tmdb.org/t/p/w500/6MKr3KgOLmzOP6MSuZERO41Lpkt.jpg",
    categories = listOf(Genre(id = 1, name = "Action"), Genre(id = 2, name = "Horror")),
    name = "The Tomorrow War",
    rating = 7.5,
    releaseDate = "2021-07-02",
    revenue = "120000",
    status = "Released",
    details = "The world is stunned when a group of time travelers arrive from the year 2051 to deliver an urgent message: Thirty years in the future, mankind is losing a global war against a deadly alien species. The only hope for survival is for soldiers and civilians from the present to be transported to the future and join the fight. Among those recruited is high school",
    isFavourite = true
)

val fakeSimilarMoviesList = listOf(
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

val fakeMovieDetailsFirstSectionUiState = MovieDetailsFirstSectionUiState.MovieDetailsFirstSection(movieDetailsUiModel = fakeMovieDetailsUiModel)
val fakeSimilarMoviesUiState = SimilarMoviesUiState.SimilarMoviesList(similarMovies = fakeSimilarMoviesList)

val fakeMovieDetailsFirstSectionUiStateLoading = MovieDetailsFirstSectionUiState.Loading(isLoading = true)
val fakeSimilarMoviesUiStateLoading = SimilarMoviesUiState.Loading(isLoading = true)

val fakeMovieDetailsFirstSectionUiStateError = MovieDetailsFirstSectionUiState.Error(customApiErrorExceptionUiModel = CustomApiExceptionUiModel.Network)
val fakeSimilarMoviesUiStateError = SimilarMoviesUiState.Error(customApiErrorExceptionUiModel = CustomApiExceptionUiModel.NoInternetConnection)
