package com.example.teldatask.data.data_sources.remote

import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movies_list.MovieDataModel
import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movies_list.MoviesResponse
import com.example.teldatask.data.mapper.toCustomApiExceptionDomainModel
import com.example.teldatask.data.data_sources.remote.retrofit.api.MoviesApi
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(
    private val moviesApi: MoviesApi
) {
    suspend fun fetchPopularMovies(): List<MovieDataModel> {
        return try {
            val moviesResponse = moviesApi.fetchPopularMovies().body() as MoviesResponse
            moviesResponse.movieListDataModel
        } catch (e: Exception) {
            throw e.toCustomApiExceptionDomainModel()
        }
    }

    suspend fun searchMovies(query: String): List<MovieDataModel> {
        return try {
            val moviesResponse = moviesApi.searchMovies(query = query).body() as MoviesResponse
            moviesResponse.movieListDataModel
        } catch (e: Exception) {
            throw e.toCustomApiExceptionDomainModel()
        }
    }
}

