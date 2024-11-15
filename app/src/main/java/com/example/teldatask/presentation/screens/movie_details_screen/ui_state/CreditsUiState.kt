package com.example.teldatask.presentation.screens.movie_details_screen.ui_state

import com.example.teldatask.presentation.model.CustomApiExceptionUiModel
import com.example.teldatask.presentation.model.CustomDatabaseExceptionUiModel
import com.example.teldatask.presentation.screens.movie_details_screen.model.CreditsUiModel

sealed class CreditsUiState{
    data class Loading(val isLoading:Boolean): CreditsUiState()
    data class Credits(val creditsUiModel: CreditsUiModel): CreditsUiState()
    data class Error(val customApiErrorExceptionUiModel: CustomApiExceptionUiModel? = null, val customDatabaseExceptionUiModel: CustomDatabaseExceptionUiModel?= null ): CreditsUiState()
}
