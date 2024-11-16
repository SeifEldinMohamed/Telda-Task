package com.example.teldatask.data.data_sources.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.teldatask.data.data_sources.local.room.entities.FavouriteMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavouriteMovie(favouriteMovieEntity: FavouriteMovieEntity)

    @Delete
    suspend fun deleteFavouriteMovie(favouriteMovieEntity: FavouriteMovieEntity)

    @Query("SELECT * FROM favorites_entity WHERE id = :movieId")
    suspend fun getFavoriteMovie(movieId: Int): FavouriteMovieEntity?

    @Query("SELECT * FROM favorites_entity")
    fun getAllFavoriteMovies(): Flow<List<FavouriteMovieEntity>>
}