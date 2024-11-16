package com.example.teldatask.domain.repository

import com.example.teldatask.domain.model.MovieDomainModel
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun fetchPopularMovies(): Flow<List<MovieDomainModel>>
    fun searchMovies(query: String): Flow<List<MovieDomainModel>>
}