package com.example.teldatask.data.mapper

import com.example.teldatask.data.Constants.Companion.IMAGE_BASE_URL
import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movies_list.MovieDataModel
import com.example.teldatask.domain.model.MovieDomainModel

fun MovieDataModel.toMovieDomainModel(): MovieDomainModel {
    return MovieDomainModel(
        id = this.id,
        title = this.title,
        posterPath = IMAGE_BASE_URL + this.posterPath,
        releaseDate = this.releaseDate,
        voteAverage = this.voteAverage,
        overview = this.overview,
        originalLanguage = this.originalLanguage
    )
}