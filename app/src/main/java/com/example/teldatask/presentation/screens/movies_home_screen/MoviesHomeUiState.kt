package com.example.teldatask.presentation.screens.movies_home_screen

import com.example.teldatask.presentation.screens.movies_home_screen.model.MovieUiModel
import com.example.teldatask.presentation.model.CustomApiExceptionUiModel
import com.example.teldatask.presentation.model.CustomDatabaseExceptionUiModel

sealed class MoviesHomeUiState{
    data object EmptyState:MoviesHomeUiState()
    data class Loading(val isLoading:Boolean):MoviesHomeUiState()
    data class PopularMoviesList(val popularMoviesList: List<MovieUiModel>):MoviesHomeUiState()
    data class SearchedMoviesList(val searchedMoviesList: List<MovieUiModel>):MoviesHomeUiState()
    data class ApiError(val customApiErrorExceptionUiModel: CustomApiExceptionUiModel):MoviesHomeUiState()
    data class DatabaseError(val customDatabaseExceptionUiModel: CustomDatabaseExceptionUiModel):MoviesHomeUiState()
}
