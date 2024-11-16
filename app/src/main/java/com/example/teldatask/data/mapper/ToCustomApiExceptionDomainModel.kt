package com.example.teldatask.data.mapper

import android.database.sqlite.SQLiteException
import com.example.teldatask.domain.model.CustomApiExceptionDomainModel
import com.example.teldatask.domain.model.CustomExceptionDomainModel
import retrofit2.HttpException
import java.io.IOException
import java.io.InterruptedIOException
import java.net.HttpURLConnection

fun Throwable.toCustomExceptionDomainModel(): CustomExceptionDomainModel {
    return when (this) {
        is InterruptedIOException -> CustomExceptionDomainModel.Api(
            CustomApiExceptionDomainModel.TimeoutExceptionDomainModel
        )
        is IOException -> CustomExceptionDomainModel.Api(
            CustomApiExceptionDomainModel.NetworkExceptionDomainModel
        )
        is HttpException -> {
            val apiException = when (this.code()) {
                HttpURLConnection.HTTP_NOT_FOUND -> CustomApiExceptionDomainModel.ServiceNotFoundExceptionDomainModel
                HttpURLConnection.HTTP_FORBIDDEN -> CustomApiExceptionDomainModel.AccessDeniedExceptionDomainModel
                HttpURLConnection.HTTP_UNAVAILABLE -> CustomApiExceptionDomainModel.ServiceUnavailableExceptionDomainModel
                else -> CustomApiExceptionDomainModel.UnknownExceptionDomainModel
            }
            CustomExceptionDomainModel.Api(apiException)
        }
        is SQLiteException -> CustomExceptionDomainModel.Database(this.toCustomDatabaseExceptionDomainModel())
        else -> CustomExceptionDomainModel.Api(CustomApiExceptionDomainModel.UnknownExceptionDomainModel)
    }
}