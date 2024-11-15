package com.example.teldatask.data.repository

import com.example.teldatask.data.data_sources.remote.MoviesRemoteDataSource
import com.example.teldatask.data.mapper.toMovieDetailsDomainModel
import com.example.teldatask.domain.model.MovieDetailsDomainModel
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
}