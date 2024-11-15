package com.example.teldatask.domain.repository

import com.example.teldatask.domain.model.MovieDomainModel

interface MoviesRepository {
    suspend fun fetchPopularMovies(): List<MovieDomainModel>
    suspend fun searchMovies(query:String): List<MovieDomainModel>
}