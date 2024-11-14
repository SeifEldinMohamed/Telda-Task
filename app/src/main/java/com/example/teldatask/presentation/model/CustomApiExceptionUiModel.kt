package com.example.teldatask.presentation.model

sealed class CustomApiExceptionUiModel {
    data object NoInternetConnection : CustomApiExceptionUiModel()
    data object Network : CustomApiExceptionUiModel()
    data object Timeout : CustomApiExceptionUiModel()
    data object ServiceUnreachable : CustomApiExceptionUiModel()
    data object Unknown : CustomApiExceptionUiModel()
}