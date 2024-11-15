package com.example.teldatask.data.mapper

import com.example.teldatask.data.Constants.Companion.IMAGE_BASE_URL
import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movie_details.credits.Cast
import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movie_details.credits.CreditsResponse
import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movie_details.credits.Crew
import com.example.teldatask.domain.model.details.CastDomainModel
import com.example.teldatask.domain.model.details.CreditsDomainModel
import com.example.teldatask.domain.model.details.CrewDomainModel

fun CreditsResponse.toCreditsDomainModel(): CreditsDomainModel {
    return CreditsDomainModel(
        id = this.id,
        crew = this.crew.map { it.toCrewDomainModel() },
        cast = this.cast.map { it.toCrewDomainModel() }
    )
}

fun Crew.toCrewDomainModel(): CrewDomainModel {
    return CrewDomainModel(
        id = this.id,
        name = this.name,
        knownForDepartment = this.knownForDepartment,
        popularity = this.popularity,
        profilePath =  IMAGE_BASE_URL + this.profilePath
    )
}

fun Cast.toCrewDomainModel(): CastDomainModel {
    return CastDomainModel(
        id = this.id,
        name = this.name,
        knownForDepartment = this.knownForDepartment,
        popularity = this.popularity,
        profilePath =  IMAGE_BASE_URL +this.profilePath
    )
}