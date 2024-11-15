package com.example.teldatask.presentation.mapper

import com.example.teldatask.domain.model.MovieDomainModel
import com.example.teldatask.presentation.screens.movies_home_screen.model.MovieUiModel

fun MovieDomainModel.toPopularMovieUIModel(): MovieUiModel {
    return MovieUiModel(
        id = this.id,
        title = this.title,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
        overview = this.overview,
        originalLanguage = this.originalLanguage,
        isFavourite = false
    )
}