package com.example.teldatask.domain.model

data class MovieDomainModel(
    val id: Int,
    val title: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double,
    val overview: String,
    val originalLanguage: String,
    val isFavourite:Boolean
)
