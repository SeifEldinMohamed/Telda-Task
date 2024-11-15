package com.example.teldatask.presentation.screens.movie_details_screen.preview_data

import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movie_details.Genre
import com.example.teldatask.presentation.screens.movie_details_screen.MovieDetailsFirstSectionUiState
import com.example.teldatask.presentation.screens.movie_details_screen.model.MovieDetailsUiModel

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

val fakeMovieDetailsFirstSectionUiState = MovieDetailsFirstSectionUiState.MovieDetailsFirstSection(movieDetailsUiModel = fakeMovieDetailsUiModel)
