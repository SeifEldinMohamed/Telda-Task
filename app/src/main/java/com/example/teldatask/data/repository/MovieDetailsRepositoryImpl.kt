package com.example.teldatask.data.repository

import com.example.teldatask.data.data_sources.remote.MoviesRemoteDataSource
import com.example.teldatask.data.mapper.toCreditsDomainModel
import com.example.teldatask.data.mapper.toMovieDetailsDomainModel
import com.example.teldatask.data.mapper.toMovieDomainModel
import com.example.teldatask.domain.model.details.MovieDetailsDomainModel
import com.example.teldatask.domain.model.MovieDomainModel
import com.example.teldatask.domain.model.details.CreditsDomainModel
import com.example.teldatask.domain.repository.MovieDetailsRepository
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
) : MovieDetailsRepository {
    override suspend fun fetchMovieDetailsFirstSection(movieId: Int): MovieDetailsDomainModel {
        return try {
            moviesRemoteDataSource.fetchMovieDetails(movieId = movieId).toMovieDetailsDomainModel()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun fetchSimilarMovies(movieId: Int): List<MovieDomainModel> {
        return try {
            moviesRemoteDataSource.fetchSimilarMovies(movieId = movieId).map { it.toMovieDomainModel() }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun fetchMovieCredits(movieId: Int): CreditsDomainModel {
        return try {
            moviesRemoteDataSource.fetchMovieCredits(movieId = movieId).toCreditsDomainModel()
        } catch (e: Exception) {
            throw e
        }
    }
}