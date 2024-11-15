package com.example.teldatask.domain.repository

import com.example.teldatask.domain.model.MovieDomainModel

interface MoviesRepository {
    suspend fun fetchPopularMovies(): List<MovieDomainModel>
}