package com.example.teldatask.data.data_sources.remote.retrofit.interceptor

import com.example.teldatask.BuildConfig
import com.example.teldatask.data.Constants.Companion.API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalUrl = originalRequest.url

        val apiKey = BuildConfig.API_KEY
        val urlWithApiKey = originalUrl.newBuilder()
            .addQueryParameter(API_KEY, apiKey)
            .build()

        val requestWithApiKey = originalRequest.newBuilder()
            .url(urlWithApiKey)
            .build()

        return chain.proceed(requestWithApiKey)
    }
}