package com.example.teldatask.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.teldatask.presentation.screens.movies_home_screen.MoviesHomeScreen

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
                  //  navController.navigate(Screens.MovieDetailsScreen.passMovieId(movieId))
                },
            )
        }
    }
}