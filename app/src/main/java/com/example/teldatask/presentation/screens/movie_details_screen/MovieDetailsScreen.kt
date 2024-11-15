package com.example.teldatask.presentation.screens.movie_details_screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.teldatask.presentation.common_components.ErrorSection
import com.example.teldatask.presentation.screens.movie_details_screen.components.Categories
import com.example.teldatask.presentation.screens.movie_details_screen.components.MovieDetailsAppBar
import com.example.teldatask.presentation.screens.movie_details_screen.components.MovieImageBanner
import com.example.teldatask.presentation.screens.movie_details_screen.components.MovieInfo
import com.example.teldatask.presentation.screens.movie_details_screen.components.MovieNameAndRating
import com.example.teldatask.presentation.screens.movie_details_screen.components.shimmer_loading.AnimateShimmerDetails
import com.example.teldatask.presentation.screens.movie_details_screen.model.MovieDetailsUiModel
import com.example.teldatask.presentation.screens.movie_details_screen.preview_data.fakeMovieDetailsFirstSectionUiState
import com.example.teldatask.presentation.screens.movie_details_screen.viewmodel.MovieDetailsViewModel
import com.example.teldatask.presentation.ui.theme.TeldaTaskTheme

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    onBackArrowPressed: () -> Unit
) {
    val movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel()
    LaunchedEffect(key1 = Unit) {
        movieDetailsViewModel.requestMovieDetailsFirstSection(movieId)
    }
    val movieDetailsFirstSectionUiState by movieDetailsViewModel.movieDetailsFirstSectionUiState.collectAsStateWithLifecycle()

    MovieDetailsContent(
        movieDetailsFirstSectionUiState,
        onRefreshClicked = {
            movieDetailsViewModel.requestMovieDetailsFirstSection(movieId)
        },
        onBackArrowPressed = onBackArrowPressed,
        makeFavoriteMovie = false,
        onFavouriteButtonClicked = { makeFavourite, movieDetailsUiModel ->

        }
    )
}

@Composable
fun MovieDetailsContent(
    movieDetailsUiState: MovieDetailsFirstSectionUiState,
    onBackArrowPressed: () -> Unit,
    onFavouriteButtonClicked: (isFavourite: Boolean, movieDetailsUiModel: MovieDetailsUiModel) -> Unit,
    onRefreshClicked: () -> Unit,
    makeFavoriteMovie: Boolean
) {

    when (movieDetailsUiState) {
        is MovieDetailsFirstSectionUiState.Loading -> {
            if (movieDetailsUiState.isLoading)
                AnimateShimmerDetails()
        }

        is MovieDetailsFirstSectionUiState.Error -> {
            if (movieDetailsUiState.customApiErrorExceptionUiModel != null) {
                ErrorSection(
                    onRefreshButtonClicked = onRefreshClicked,
                    customApiErrorExceptionUiModel = movieDetailsUiState.customApiErrorExceptionUiModel
                )
            } else {
                ErrorSection(
                    onRefreshButtonClicked = onRefreshClicked,
                    customDatabaseExceptionUiModel = movieDetailsUiState.customDatabaseExceptionUiModel
                )
            }

        }

        is MovieDetailsFirstSectionUiState.MovieDetailsFirstSection -> {
            val movieDetailsUiModel = movieDetailsUiState.movieDetailsUiModel

            val scrollState = rememberScrollState()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(scrollState)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    MovieImageBanner(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp),
                        movieImage = movieDetailsUiModel.image,
                    )

                    MovieNameAndRating(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                        movieName = movieDetailsUiModel.name,
                        rating = movieDetailsUiModel.rating.toFloat(),
                    )

                    MovieInfo(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        movieDetails = movieDetailsUiModel.details,
                        movieReleaseDate = movieDetailsUiModel.releaseDate,
                        revenue = movieDetailsUiModel.revenue,
                        status = movieDetailsUiModel.status
                    )

                    Categories(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        categories = movieDetailsUiModel.categories
                    )

                }

                MovieDetailsAppBar(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopCenter)
                        .fillMaxWidth(),
                    isLiked = makeFavoriteMovie,
                    onFavouriteButtonClicked = {
                        Log.d("favourite", "in onFavouriteButtonClicked $it")
                        onFavouriteButtonClicked(it, movieDetailsUiModel)
                    },
                    onBackArrowPressed = onBackArrowPressed
                )
            }
        }

    }
}

@Preview
@Composable
fun FilmDetailsScreenPreview() {
    TeldaTaskTheme {
        MovieDetailsContent(
            movieDetailsUiState = fakeMovieDetailsFirstSectionUiState,
            onBackArrowPressed = {},
            onFavouriteButtonClicked = { _, _ -> },
            {},
            makeFavoriteMovie = false
        )
    }
}
