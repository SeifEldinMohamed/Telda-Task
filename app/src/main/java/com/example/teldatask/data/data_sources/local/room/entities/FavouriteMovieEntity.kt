package com.example.teldatask.data.data_sources.local.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.teldatask.data.Constants.Companion.FAVOURITE_MOVIE_ENTITY

@Entity(tableName = FAVOURITE_MOVIE_ENTITY)
data class FavouriteMovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val overview: String,
    val isFavourite: Boolean
)
