package com.example.teldatask.presentation.screens.movie_details_screen

import com.example.teldatask.presentation.model.CustomApiExceptionUiModel
import com.example.teldatask.presentation.model.CustomDatabaseExceptionUiModel
import com.example.teldatask.presentation.screens.movie_details_screen.model.MovieDetailsUiModel

sealed class MovieDetailsFirstSectionUiState{
    data class Loading(val isLoading:Boolean):MovieDetailsFirstSectionUiState()
    data class MovieDetailsFirstSection(val movieDetailsUiModel: MovieDetailsUiModel):MovieDetailsFirstSectionUiState()
    data class Error(val customApiErrorExceptionUiModel: CustomApiExceptionUiModel? = null, val customDatabaseExceptionUiModel: CustomDatabaseExceptionUiModel?= null ):MovieDetailsFirstSectionUiState()
}
