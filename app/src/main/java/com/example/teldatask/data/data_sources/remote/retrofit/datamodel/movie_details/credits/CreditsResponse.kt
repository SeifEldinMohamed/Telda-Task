package com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movie_details.credits

data class CreditsResponse(
    val id: Int,
    val cast: List<Cast>,
    val crew: List<Crew>,
)