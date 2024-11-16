package com.example.teldatask.data.repository

import com.example.teldatask.data.data_sources.local.MoviesLocalDataSource
import com.example.teldatask.data.mapper.toMovieDomainModel
import com.example.teldatask.data.data_sources.remote.MoviesRemoteDataSource
import com.example.teldatask.domain.model.MovieDomainModel
import com.example.teldatask.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val moviesLocalDataSource: MoviesLocalDataSource
) : MoviesRepository {
    override suspend fun fetchPopularMovies(): Flow<List<MovieDomainModel>> = flow {
        try {
            val moviesFromApi = moviesRemoteDataSource.fetchPopularMovies()
            emit(moviesFromApi)
        } catch (e: Exception) {
            throw e
        }
    }.combine(
        moviesLocalDataSource.getAllFavoriteMovies()
    ) { moviesFromApi, favoriteMovies ->
        moviesFromApi.map { it.toMovieDomainModel() }.map { movie ->
            val isFavourite = favoriteMovies.any { it.id == movie.id }
            movie.copy(isFavourite = isFavourite)
        }
    }

    override suspend fun searchMovies(query: String): List<MovieDomainModel> {
        return try {
            moviesRemoteDataSource.searchMovies(query = query).map { it.toMovieDomainModel() }
        } catch (e: Exception) {
            throw e
        }
    }

}