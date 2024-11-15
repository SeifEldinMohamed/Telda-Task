package com.example.teldatask.data.mapper

import com.example.teldatask.data.Constants.Companion.IMAGE_BASE_URL
import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movie_details.MovieDetailsResponse
import com.example.teldatask.domain.model.details.MovieDetailsDomainModel

fun MovieDetailsResponse.toMovieDetailsDomainModel(): MovieDetailsDomainModel {
    return MovieDetailsDomainModel(
        id = this.id,
        name = this.originalTitle,
        posterPath = IMAGE_BASE_URL + this.posterPath,
        releaseDate = this.releaseDate,
        rating = this.voteAverage,
        revenue = this.revenue.toString(),
        status = this.status,
        details = this.overview,
        categories = this.genres,
        isFavourite = false
    )
}
