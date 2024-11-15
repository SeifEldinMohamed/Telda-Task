package com.example.teldatask.domain.repository

import com.example.teldatask.domain.model.MovieDetailsDomainModel

interface MovieDetailsRepository {
    suspend fun fetchMovieDetailsFirstSection(movieId: Int): MovieDetailsDomainModel
}