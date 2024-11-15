package com.example.teldatask.presentation.mapper

import com.example.teldatask.domain.model.MovieDetailsDomainModel
import com.example.teldatask.presentation.screens.movie_details_screen.model.MovieDetailsUiModel

fun MovieDetailsDomainModel.toMovieDetailsUIModel(): MovieDetailsUiModel {
    return MovieDetailsUiModel(
        id = this.id,
        name = this.name,
        image = this.posterPath,
        releaseDate = this.releaseDate,
        rating = this.rating,
        revenue = this.revenue,
        status = this.status,
        details = this.details,
        categories = this.categories,
        isFavourite = isFavourite
    )
}