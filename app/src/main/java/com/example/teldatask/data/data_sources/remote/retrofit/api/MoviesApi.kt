package com.example.teldatask.data.data_sources.remote.retrofit.api

import com.example.teldatask.data.Constants.Companion.LANGUAGE_KEY
import com.example.teldatask.data.Constants.Companion.POPULAR_MOVIES_ENDPOINT
import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movies_list.PopularMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {
    @GET(POPULAR_MOVIES_ENDPOINT)
    suspend fun fetchPopularMovies(
        @Query(LANGUAGE_KEY) language: String = "en"
    ): Response<PopularMoviesResponse>
}