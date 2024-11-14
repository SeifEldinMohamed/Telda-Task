package com.example.teldatask.presentation.navigation

import com.example.teldatask.presentation.utils.Constants.Companion.MOVIE_DETAILS
import com.example.teldatask.presentation.utils.Constants.Companion.MOVIE_ID_KEY

sealed class Screens(val route: String) {
    data object MoviesListScreen: Screens("movies_list_screen")
    data object MovieDetailsScreen: Screens("$MOVIE_DETAILS/{$MOVIE_ID_KEY}"){
        fun passMovieId(movieId:Int):String {
            return "$MOVIE_DETAILS/$movieId"
        }
    }
}