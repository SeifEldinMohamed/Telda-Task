package com.example.teldatask.presentation.screens.movies_home_screen.model

data class MovieUiModel(
    val id: Int,
    val title: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val overview: String,
    val originalLanguage: String,
    val isFavourite:Boolean
)
