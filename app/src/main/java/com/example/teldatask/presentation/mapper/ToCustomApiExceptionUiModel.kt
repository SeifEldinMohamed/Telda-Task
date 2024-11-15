package com.example.teldatask.presentation.mapper

import com.example.teldatask.domain.model.CustomApiExceptionDomainModel
import com.example.teldatask.presentation.model.CustomApiExceptionUiModel

fun CustomApiExceptionDomainModel.toCustomApiExceptionUiModel(): CustomApiExceptionUiModel {
    return when (this) {
        is CustomApiExceptionDomainModel.NoInternetConnectionExceptionDomainModel -> CustomApiExceptionUiModel.NoInternetConnection
        is CustomApiExceptionDomainModel.TimeoutExceptionDomainModel -> CustomApiExceptionUiModel.Timeout
        is CustomApiExceptionDomainModel.NetworkExceptionDomainModel -> CustomApiExceptionUiModel.Network
        is CustomApiExceptionDomainModel.ServiceNotFoundExceptionDomainModel, CustomApiExceptionDomainModel.AccessDeniedExceptionDomainModel, CustomApiExceptionDomainModel.ServiceUnavailableExceptionDomainModel ->
            CustomApiExceptionUiModel.ServiceUnreachable

        else ->{
            CustomApiExceptionUiModel.Unknown
        }

    }
}