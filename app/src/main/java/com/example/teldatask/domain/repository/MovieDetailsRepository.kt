package com.example.teldatask.domain.repository

import com.example.teldatask.domain.model.MovieDetailsDomainModel
import com.example.teldatask.domain.model.MovieDomainModel

interface MovieDetailsRepository {
    suspend fun fetchMovieDetailsFirstSection(movieId: Int): MovieDetailsDomainModel
    suspend fun fetchSimilarMovies(movieId:Int): List<MovieDomainModel>
}