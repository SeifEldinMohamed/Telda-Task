package com.example.teldatask.data.mapper

import com.example.teldatask.data.data_sources.local.room.entities.FavouriteMovieEntity
import com.example.teldatask.presentation.screens.movie_details_screen.model.MovieDetailsUiModel

fun MovieDetailsUiModel.toFavouriteMovieEntity(): FavouriteMovieEntity {
    return FavouriteMovieEntity(
        id = this.id,
        title = this.name,
        overview = this.details,
        isFavourite = false
    )
}