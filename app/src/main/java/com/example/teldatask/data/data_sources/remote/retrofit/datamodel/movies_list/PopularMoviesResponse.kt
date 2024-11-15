package com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movies_list

import com.google.gson.annotations.SerializedName

data class PopularMoviesResponse(
    val page: Int,
    @SerializedName("results")
    val movieListDataModel: List<MovieDataModel>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)