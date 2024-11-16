package com.example.teldatask.data.data_sources.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.teldatask.data.data_sources.local.room.entities.FavouriteMovieEntity

@Database(
    entities = [FavouriteMovieEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FavouriteDataBase : RoomDatabase() {
    abstract fun favouriteDao(): FavouriteDao
}