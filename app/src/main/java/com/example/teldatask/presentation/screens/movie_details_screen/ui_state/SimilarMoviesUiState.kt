package com.example.teldatask.presentation.screens.movie_details_screen.ui_state

import com.example.teldatask.presentation.model.CustomApiExceptionUiModel
import com.example.teldatask.presentation.model.CustomDatabaseExceptionUiModel
import com.example.teldatask.presentation.screens.movie_details_screen.model.MovieDetailsUiModel
import com.example.teldatask.presentation.screens.movies_home_screen.model.MovieUiModel

sealed class SimilarMoviesUiState{
    data class Loading(val isLoading:Boolean): SimilarMoviesUiState()
    data class SimilarMoviesList(val similarMovies: List<MovieUiModel>): SimilarMoviesUiState()
    data class Error(val customApiErrorExceptionUiModel: CustomApiExceptionUiModel? = null, val customDatabaseExceptionUiModel: CustomDatabaseExceptionUiModel?= null ): SimilarMoviesUiState()
}
