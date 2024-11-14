package com.example.teldatask.presentation.screens.movies_home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.teldatask.R
import com.example.teldatask.presentation.common_components.CenterAlignedAppBar
import com.example.teldatask.presentation.common_components.EmptySection
import com.example.teldatask.presentation.common_components.ErrorSection
import com.example.teldatask.presentation.screens.movies_home_screen.components.MovieItem
import com.example.teldatask.presentation.screens.movies_home_screen.components.shimmer_loading.AnimateShimmerMoviesList
import com.example.teldatask.presentation.screens.movies_home_screen.preview_data.fakeMoviesHomeUiState
import com.example.teldatask.presentation.ui.theme.TeldaTaskTheme


@Composable
fun MoviesHomeScreen(
    onMovieClick: (id: Int) -> Unit,
) {

    MoviesHomeContent(
        moviesHomeUiState = fakeMoviesHomeUiState,
        onMovieClick = onMovieClick,
        onRefreshButtonClicked = {

        }
    )
}

@Composable
fun MoviesHomeContent(
    moviesHomeUiState: MoviesHomeUiState,
    onMovieClick: (id: Int) -> Unit,
    onRefreshButtonClicked: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        CenterAlignedAppBar(
            title = R.string.app_name,
            showBackButton = false
        )

        when(moviesHomeUiState) {
            is MoviesHomeUiState.EmptyState -> {
                EmptySection()
            }
            is MoviesHomeUiState.Loading -> {
                if (moviesHomeUiState.isLoading) {
                    Spacer(modifier = Modifier.height(8.dp))
                    AnimateShimmerMoviesList()
                }
            }

            is MoviesHomeUiState.ApiError -> {
                ErrorSection(
                    onRefreshButtonClicked = onRefreshButtonClicked,
                    customApiErrorExceptionUiModel = moviesHomeUiState.customApiErrorExceptionUiModel
                )
            }
            is MoviesHomeUiState.DatabaseError -> {
                ErrorSection(
                    onRefreshButtonClicked = onRefreshButtonClicked,
                    customDatabaseExceptionUiModel = moviesHomeUiState.customDatabaseExceptionUiModel
                )
            }
            is MoviesHomeUiState.SearchedMoviesList -> {
                LazyColumn(
                    Modifier.padding(vertical = 8.dp)
                ) {
                    items(moviesHomeUiState.searchedMoviesList) { movieUiModel ->
                        MovieItem(
                            movieUiModel = movieUiModel,
                            onMovieClick = onMovieClick,
                        )
                    }
                }
            }

            is MoviesHomeUiState.PopularMoviesList -> {
                LazyColumn(
                    Modifier.padding(vertical = 8.dp)
                ) {
                    items(moviesHomeUiState.popularMoviesList) { movieUiModel ->
                        MovieItem(
                            movieUiModel = movieUiModel,
                            onMovieClick = onMovieClick,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFEBEBEB)
@Composable
private fun PreviewPopularMoviesContent() {
    TeldaTaskTheme {
        MoviesHomeContent(
            moviesHomeUiState = fakeMoviesHomeUiState,
            onMovieClick = {},
            onRefreshButtonClicked = {}
        )
    }
}
