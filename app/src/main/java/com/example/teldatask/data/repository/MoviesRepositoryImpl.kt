package com.example.teldatask.data.repository

import com.example.teldatask.data.data_sources.local.MoviesLocalDataSource
import com.example.teldatask.data.data_sources.remote.MoviesRemoteDataSource
import com.example.teldatask.data.mapper.toMovieDomainModel
import com.example.teldatask.domain.model.MovieDomainModel
import com.example.teldatask.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val moviesLocalDataSource: MoviesLocalDataSource
) : MoviesRepository {
    override  fun fetchPopularMovies(): Flow<List<MovieDomainModel>> = flow {
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

    override fun searchMovies(query: String): Flow<List<MovieDomainModel>> = flow {
        try {
            val searchMoviesFromApi = moviesRemoteDataSource.searchMovies(query = query)
                .map { it.toMovieDomainModel() }

            val favoriteMovies = moviesLocalDataSource.getAllFavoriteMovies().firstOrNull().orEmpty()

            val mergedMovies = searchMoviesFromApi.map { movie ->
                val isFavourite = favoriteMovies.any { it.id == movie.id }
                movie.copy(isFavourite = isFavourite)
            }

            emit(mergedMovies)

        } catch (e: Exception) {
            throw e
        }
    }

}