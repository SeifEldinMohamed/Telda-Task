package com.example.teldatask.domain.repository

import com.example.teldatask.domain.model.MovieDomainModel
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    suspend fun fetchPopularMovies(): Flow<List<MovieDomainModel>>
    suspend fun searchMovies(query: String): List<MovieDomainModel>
}