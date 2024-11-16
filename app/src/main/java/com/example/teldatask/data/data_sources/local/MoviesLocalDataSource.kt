package com.example.teldatask.data.data_sources.local

import com.example.teldatask.data.data_sources.local.room.FavouriteDao
import com.example.teldatask.data.data_sources.local.room.entities.FavouriteMovieEntity
import com.example.teldatask.data.mapper.toCustomExceptionDomainModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesLocalDataSource @Inject constructor(
    private val favouriteDao: FavouriteDao
) {

    suspend fun insertFavouriteMovie(favouriteMovieEntity: FavouriteMovieEntity) {
        try {
            favouriteDao.insertFavouriteMovie(favouriteMovieEntity)
        } catch (e: Exception) {
            throw e.toCustomExceptionDomainModel()
        }
    }

    suspend fun deleteFavouriteMovie(favouriteMovieEntity: FavouriteMovieEntity) {
        try {
            favouriteDao.deleteFavouriteMovie(favouriteMovieEntity)
        } catch (e: Exception) {
            throw e.toCustomExceptionDomainModel()
        }
    }

    suspend fun getFavouriteMovie(movieId: Int): FavouriteMovieEntity? {
        try {
            return favouriteDao.getFavoriteMovie(movieId)
        } catch (e: Exception) {
            throw e.toCustomExceptionDomainModel()
        }
    }

    fun getAllFavoriteMovies(): Flow<List<FavouriteMovieEntity>> {
        try {
            return favouriteDao.getAllFavoriteMovies()
        } catch (e: Exception) {
            throw e.toCustomExceptionDomainModel()
        }
    }

}
