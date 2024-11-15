package com.example.teldatask.data.data_sources.remote

import com.example.teldatask.data.data_sources.remote.retrofit.api.MovieDetailsApi
import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movies_list.MovieDataModel
import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movies_list.MoviesResponse
import com.example.teldatask.data.mapper.toCustomApiExceptionDomainModel
import com.example.teldatask.data.data_sources.remote.retrofit.api.MoviesApi
import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movie_details.MovieDetailsResponse
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(
    private val moviesApi: MoviesApi,
    private val movieDetailsApi: MovieDetailsApi

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

    suspend fun fetchMovieDetails(movieId: Int): MovieDetailsResponse {
        return try {
            movieDetailsApi.fetchMovieDetails(movieId = movieId).body() as MovieDetailsResponse
        } catch (e:Exception) {
            throw e.toCustomApiExceptionDomainModel()
        }
    }

    suspend fun fetchSimilarMovies(movieId: Int): List<MovieDataModel> {
        return try {
            val similarMoviesResponse = movieDetailsApi.fetchSimilarMovies(movieId = movieId).body() as MoviesResponse
            similarMoviesResponse.movieListDataModel
        } catch (e:Exception) {
            throw e.toCustomApiExceptionDomainModel()
        }
    }
}

