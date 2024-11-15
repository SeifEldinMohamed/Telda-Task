package com.example.teldatask.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.teldatask.presentation.screens.movie_details_screen.MovieDetailsScreen
import com.example.teldatask.presentation.screens.movies_home_screen.MoviesHomeScreen
import com.example.teldatask.presentation.utils.Constants.Companion.MOVIE_ID_KEY

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.MoviesListScreen.route
    ) {
        composable(route = Screens.MoviesListScreen.route) {
            MoviesHomeScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screens.MovieDetailsScreen.passMovieId(movieId))
                },
            )
        }

        composable(
            route = Screens.MovieDetailsScreen.route,
            arguments = listOf(navArgument(MOVIE_ID_KEY) {
                type = NavType.IntType
            })
        ) {
            val movieId = it.arguments?.getInt(MOVIE_ID_KEY)
            movieId?.let { id ->
                MovieDetailsScreen(
                    movieId = id,
                    onBackArrowPressed = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}