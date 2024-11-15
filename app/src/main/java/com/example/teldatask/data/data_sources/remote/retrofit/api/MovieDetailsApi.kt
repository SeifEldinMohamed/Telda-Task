package com.example.teldatask.data.data_sources.remote.retrofit.api

import com.example.teldatask.data.Constants.Companion.LANGUAGE_KEY
import com.example.teldatask.data.Constants.Companion.MOVIE_CREDITS_ENDPOINT
import com.example.teldatask.data.Constants.Companion.MOVIE_DETAILS_ENDPOINT
import com.example.teldatask.data.Constants.Companion.SIMILAR_MOVIES_ENDPOINT
import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movie_details.MovieDetailsResponse
import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movie_details.credits.CreditsResponse
import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movies_list.MoviesResponse
import com.example.teldatask.presentation.utils.Constants.Companion.MOVIE_ID_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDetailsApi {
    @GET(MOVIE_DETAILS_ENDPOINT)
    suspend fun fetchMovieDetails(
        @Path(MOVIE_ID_KEY) movieId: Int,
        @Query(LANGUAGE_KEY) language: String = "en"
    ): Response<MovieDetailsResponse>

    @GET(SIMILAR_MOVIES_ENDPOINT)
    suspend fun fetchSimilarMovies(
        @Path(MOVIE_ID_KEY) movieId: Int,
        @Query(LANGUAGE_KEY) language: String = "en"
    ): Response<MoviesResponse>

    @GET(MOVIE_CREDITS_ENDPOINT)
    suspend fun fetchMovieCredits(
        @Path(MOVIE_ID_KEY) movieId: Int,
        @Query(LANGUAGE_KEY) language: String = "en"
    ): Response<CreditsResponse>
}