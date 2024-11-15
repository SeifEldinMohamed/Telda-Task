package com.example.teldatask.data.repository

import com.example.teldatask.data.mapper.toMovieDomainModel
import com.example.teldatask.data.data_sources.remote.MoviesRemoteDataSource
import com.example.teldatask.domain.model.MovieDomainModel
import com.example.teldatask.domain.repository.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
) : MoviesRepository {
    override suspend fun fetchPopularMovies(): List<MovieDomainModel> {
       return try {
            moviesRemoteDataSource.fetchPopularMovies().map { it.toMovieDomainModel() }
        } catch (e: Exception) {
            throw e
        }
    }
    override suspend fun searchMovies(query:String): List<MovieDomainModel> {
       return try {
            moviesRemoteDataSource.searchMovies(query = query).map { it.toMovieDomainModel() }
        } catch (e: Exception) {
            throw e
        }
    }
}