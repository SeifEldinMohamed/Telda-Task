package com.example.teldatask.domain.model.details

data class CreditsDomainModel(
    val id: Int,
    val cast: List<CastDomainModel>,
    val crew: List<CrewDomainModel>,
)