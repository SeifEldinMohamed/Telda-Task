package com.example.teldatask.data.data_sources.remote

import android.util.Log
import com.example.teldatask.data.data_sources.remote.retrofit.api.MovieDetailsApi
import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movies_list.MovieDataModel
import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movies_list.MoviesResponse
import com.example.teldatask.data.data_sources.remote.retrofit.api.MoviesApi
import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movie_details.MovieDetailsResponse
import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movie_details.credits.CreditsResponse
import com.example.teldatask.data.mapper.toCustomExceptionDomainModel
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
            throw e.toCustomExceptionDomainModel()
        }
    }

    suspend fun searchMovies(query: String): List<MovieDataModel> {
        return try {
            val moviesResponse = moviesApi.searchMovies(query = query).body() as MoviesResponse
            moviesResponse.movieListDataModel
        } catch (e: Exception) {
            throw e.toCustomExceptionDomainModel()
        }
    }

    suspend fun fetchMovieDetails(movieId: Int): MovieDetailsResponse {
        return try {
            movieDetailsApi.fetchMovieDetails(movieId = movieId).body() as MovieDetailsResponse
        } catch (e:Exception) {
            throw e.toCustomExceptionDomainModel()
        }
    }

    suspend fun fetchSimilarMovies(movieId: Int): List<MovieDataModel> {
        return try {
            val similarMoviesResponse = movieDetailsApi.fetchSimilarMovies(movieId = movieId).body() as MoviesResponse
            similarMoviesResponse.movieListDataModel
        } catch (e:Exception) {
            throw e.toCustomExceptionDomainModel()
        }
    }

    suspend fun fetchMovieCredits(movieId: Int): CreditsResponse {
        return try {
           val result = movieDetailsApi.fetchMovieCredits(movieId).body() as CreditsResponse
            Log.d("Credits", "insideMoviesRemoteDataSource creditResponse = $result")
            result
        } catch (e:Exception) {
            Log.d("Credits", "insideMoviesRemoteDataSource Error = $e")
            throw e.toCustomExceptionDomainModel()
        }
    }
}

