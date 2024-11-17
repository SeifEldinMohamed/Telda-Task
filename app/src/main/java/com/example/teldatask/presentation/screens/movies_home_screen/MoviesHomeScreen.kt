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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.teldatask.R
import com.example.teldatask.presentation.common_components.CenterAlignedAppBar
import com.example.teldatask.presentation.common_components.EmptySection
import com.example.teldatask.presentation.common_components.ErrorSection
import com.example.teldatask.presentation.screens.movies_home_screen.components.MovieItem
import com.example.teldatask.presentation.screens.movies_home_screen.components.SearchBar
import com.example.teldatask.presentation.screens.movies_home_screen.components.shimmer_loading.AnimateShimmerMoviesList
import com.example.teldatask.presentation.screens.movies_home_screen.preview_data.fakeMoviesHomeUiState
import com.example.teldatask.presentation.screens.movies_home_screen.preview_data.fakeMoviesHomeUiStateEmptyState
import com.example.teldatask.presentation.screens.movies_home_screen.preview_data.fakeMoviesHomeUiStateError
import com.example.teldatask.presentation.screens.movies_home_screen.preview_data.fakeMoviesHomeUiStateLoading
import com.example.teldatask.presentation.ui.theme.TeldaTaskTheme


@Composable
fun MoviesHomeScreen(
    onMovieClick: (id: Int) -> Unit,
) {
    val moviesHomeViewModel: MoviesHomeViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        moviesHomeViewModel.requestPopularMovies()
        moviesHomeViewModel.observeSearchQuery()
    }
    val moviesHomeUiState by moviesHomeViewModel.moviesHomeUiState.collectAsStateWithLifecycle()
    val searchQuery by moviesHomeViewModel.searchQuery.collectAsStateWithLifecycle()

    val keyboardController = LocalSoftwareKeyboardController.current
    MoviesHomeContent(
        moviesHomeUiState = moviesHomeUiState,
        onMovieClick = {
            onMovieClick(it)
            moviesHomeViewModel.resetSearchQuery()
            keyboardController?.hide()
        },
        searchQuery = searchQuery,
        onQueryChanged = { query ->
            moviesHomeViewModel.onSearchQueryChanged(query)
        },
        onRefreshButtonClicked = {
            moviesHomeViewModel.requestPopularMovies()
        }
    )
}

@Composable
fun MoviesHomeContent(
    moviesHomeUiState: MoviesHomeUiState,
    searchQuery: String,
    onMovieClick: (id: Int) -> Unit,
    onQueryChanged: (String) -> Unit,
    onRefreshButtonClicked: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .testTag("MoviesHomeContent")
    ) {
        CenterAlignedAppBar(
            title = R.string.app_name,
            showBackButton = false
        )

        SearchBar(
            query = searchQuery,
            onQueryChanged = onQueryChanged
        )

        when (moviesHomeUiState) {
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
                        .testTag("SearchedMoviesList")
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
                        .testTag("PopularMoviesList")
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

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewMoviesHomeContent() {
    TeldaTaskTheme {
        MoviesHomeContent(
            moviesHomeUiState = fakeMoviesHomeUiState,
            onMovieClick = {},
            searchQuery = "",
            onQueryChanged = {},
            onRefreshButtonClicked = {}
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewMoviesHomeError() {
    TeldaTaskTheme {
        MoviesHomeContent(
            moviesHomeUiState = fakeMoviesHomeUiStateError,
            onMovieClick = {},
            searchQuery = "",
            onQueryChanged = {},
            onRefreshButtonClicked = {}
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewMoviesHomeLoading() {
    TeldaTaskTheme {
        MoviesHomeContent(
            moviesHomeUiState = fakeMoviesHomeUiStateLoading,
            onMovieClick = {},
            searchQuery = "",
            onQueryChanged = {},
            onRefreshButtonClicked = {}
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewMoviesHomeEmptyState() {
    TeldaTaskTheme {
        MoviesHomeContent(
            moviesHomeUiState = fakeMoviesHomeUiStateEmptyState,
            onMovieClick = {},
            searchQuery = "",
            onQueryChanged = {},
            onRefreshButtonClicked = {}
        )
    }
}



