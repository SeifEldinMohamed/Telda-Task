package com.example.teldatask.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.teldatask.R
import com.example.teldatask.presentation.model.CustomApiExceptionUiModel
import com.example.teldatask.presentation.model.CustomDatabaseExceptionUiModel

@Composable
fun getErrorMessage(
    apiError: CustomApiExceptionUiModel?,
    databaseError: CustomDatabaseExceptionUiModel?
): String? {
    val apiErrorMessage = apiError?.let {
        when (it) {
            is CustomApiExceptionUiModel.Timeout -> stringResource(R.string.timeout_exception_message)
            is CustomApiExceptionUiModel.NoInternetConnection -> stringResource(R.string.no_internet_connection_exception_message)
            is CustomApiExceptionUiModel.Network -> stringResource(R.string.network_exception_message)
            is CustomApiExceptionUiModel.ServiceUnreachable -> stringResource(R.string.service_unreachable_exception_message)
            is CustomApiExceptionUiModel.Unknown -> stringResource(R.string.unknown_exception_message)
        }
    }

    val databaseErrorMessage = databaseError?.let {
        when (it) {
            is CustomDatabaseExceptionUiModel.DatabaseError -> stringResource(R.string.database_exception_message)
            is CustomDatabaseExceptionUiModel.Unknown -> stringResource(R.string.unknown_exception_message)
        }
    }

    return apiErrorMessage ?: databaseErrorMessage
}