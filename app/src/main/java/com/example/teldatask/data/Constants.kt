package com.example.teldatask.data

import com.example.teldatask.presentation.utils.Constants.Companion.MOVIE_ID_KEY

class Constants {
    companion object {
        const val API_KEY_VALUE = "a54c8998e93d17a30b86a6efdd4f3a7c"
        const val API_KEY = "api_key"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/"

        const val POPULAR_MOVIES_ENDPOINT = "movie/popular"
        const val MOVIE_DETAILS_ENDPOINT = "movie/{$MOVIE_ID_KEY}"
        const val SEARCH_MOVIES_ENDPOINT = "search/movie"
        const val SIMILAR_MOVIES_ENDPOINT = "movie/{$MOVIE_ID_KEY}/similar"
        const val MOVIE_CREDITS_ENDPOINT = "movie/{$MOVIE_ID_KEY}/credits"
        const val LANGUAGE_KEY = "language"
        const val QUERY_KEY = "query"

        const val DATABASE_NAME = "favorites_database"
        const val FAVOURITE_MOVIE_ENTITY = "favorites_entity"
    }
}