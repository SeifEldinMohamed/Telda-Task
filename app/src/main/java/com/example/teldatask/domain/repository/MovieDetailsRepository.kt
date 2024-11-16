package com.example.teldatask.domain.repository

import com.example.teldatask.domain.model.details.MovieDetailsDomainModel
import com.example.teldatask.domain.model.MovieDomainModel
import com.example.teldatask.domain.model.details.CreditsDomainModel
import com.example.teldatask.presentation.screens.movie_details_screen.model.MovieDetailsUiModel

interface MovieDetailsRepository {
    suspend fun fetchMovieDetailsFirstSection(movieId: Int): MovieDetailsDomainModel
    suspend fun fetchSimilarMovies(movieId:Int): List<MovieDomainModel>
    suspend fun fetchMovieCredits(movieId: Int): CreditsDomainModel
    suspend fun toggleFavoriteStatus(movie: MovieDetailsUiModel)
    suspend fun isFavorite(movieId: Int): Boolean
}