package com.example.teldatask.data.mapper

import com.example.teldatask.domain.model.CustomApiExceptionDomainModel
import retrofit2.HttpException
import java.io.IOException
import java.io.InterruptedIOException
import java.net.HttpURLConnection

fun Throwable.toCustomApiExceptionDomainModel(): CustomApiExceptionDomainModel {
    return when (this) {
        is InterruptedIOException -> CustomApiExceptionDomainModel.TimeoutExceptionDomainModel
        is IOException -> CustomApiExceptionDomainModel.NetworkExceptionDomainModel
        is HttpException -> {
            when (this.code()) {
                HttpURLConnection.HTTP_NOT_FOUND -> CustomApiExceptionDomainModel.ServiceNotFoundExceptionDomainModel
                HttpURLConnection.HTTP_FORBIDDEN -> CustomApiExceptionDomainModel.AccessDeniedExceptionDomainModel
                HttpURLConnection.HTTP_UNAVAILABLE -> CustomApiExceptionDomainModel.ServiceUnavailableExceptionDomainModel
                else -> CustomApiExceptionDomainModel.UnknownExceptionDomainModel
            }
        }

        else -> CustomApiExceptionDomainModel.UnknownExceptionDomainModel
    }
}