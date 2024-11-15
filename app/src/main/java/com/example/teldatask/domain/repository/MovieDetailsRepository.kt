package com.example.teldatask.domain.repository

import com.example.teldatask.domain.model.details.MovieDetailsDomainModel
import com.example.teldatask.domain.model.MovieDomainModel
import com.example.teldatask.domain.model.details.CreditsDomainModel

interface MovieDetailsRepository {
    suspend fun fetchMovieDetailsFirstSection(movieId: Int): MovieDetailsDomainModel
    suspend fun fetchSimilarMovies(movieId:Int): List<MovieDomainModel>
    suspend fun fetchMovieCredits(movieId: Int): CreditsDomainModel
}