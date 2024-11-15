package com.example.teldatask.presentation.mapper

import com.example.teldatask.domain.model.details.CastDomainModel
import com.example.teldatask.domain.model.details.CreditsDomainModel
import com.example.teldatask.domain.model.details.CrewDomainModel
import com.example.teldatask.presentation.screens.movie_details_screen.model.CastUiModel
import com.example.teldatask.presentation.screens.movie_details_screen.model.CreditsUiModel
import com.example.teldatask.presentation.screens.movie_details_screen.model.CrewUiModel

fun CreditsDomainModel.toCreditsUiModel(): CreditsUiModel {
    return CreditsUiModel(
        actors = this.cast.map { it.toCastUiModel() },
        directors = this.crew.map { it.toCrewUiModel() }
    )
}


fun CastDomainModel.toCastUiModel(): CastUiModel {
    return CastUiModel(
        id = this.id,
        name = this.name,
        popularity = this.popularity,
        profilePath = this.profilePath
    )
}

fun CrewDomainModel.toCrewUiModel(): CrewUiModel {
    return CrewUiModel(
        id = this.id,
        name = this.name,
        popularity = this.popularity,
        profilePath = this.profilePath
    )
}
