package com.example.teldatask.presentation.model

sealed class CustomDatabaseExceptionUiModel {
    data object DatabaseError : CustomDatabaseExceptionUiModel()
    data object Unknown : CustomDatabaseExceptionUiModel()
}