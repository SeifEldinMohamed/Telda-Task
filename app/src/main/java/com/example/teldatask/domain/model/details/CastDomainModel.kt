package com.example.teldatask.domain.model.details


data class CastDomainModel(
    val id: Int,
    val name: String,
    val popularity: Double,
    val profilePath: String,
    val knownForDepartment: String,
)