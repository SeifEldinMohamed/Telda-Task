package com.example.teldatask.presentation.screens.movie_details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.teldatask.R
import com.example.teldatask.presentation.common_components.ErrorSection
import com.example.teldatask.presentation.screens.movie_details_screen.components.CastItem
import com.example.teldatask.presentation.screens.movie_details_screen.components.Categories
import com.example.teldatask.presentation.screens.movie_details_screen.components.MovieDetailsAppBar
import com.example.teldatask.presentation.screens.movie_details_screen.components.MovieImageBanner
import com.example.teldatask.presentation.screens.movie_details_screen.components.MovieInfo
import com.example.teldatask.presentation.screens.movie_details_screen.components.MovieNameAndRating
import com.example.teldatask.presentation.screens.movie_details_screen.components.SimilarMovieItem
import com.example.teldatask.presentation.screens.movie_details_screen.components.shimmer_loading.AnimateShimmerDetails
import com.example.teldatask.presentation.screens.movie_details_screen.components.shimmer_loading.AnimateShimmerSimilarMovies
import com.example.teldatask.presentation.screens.movie_details_screen.model.MovieDetailsUiModel
import com.example.teldatask.presentation.screens.movie_details_screen.preview_data.fakeCreditsUiState
import com.example.teldatask.presentation.screens.movie_details_screen.preview_data.fakeCreditsUiStateError
import com.example.teldatask.presentation.screens.movie_details_screen.preview_data.fakeCreditsUiStateLoading
import com.example.teldatask.presentation.screens.movie_details_screen.preview_data.fakeMovieDetailsFirstSectionUiState
import com.example.teldatask.presentation.screens.movie_details_screen.preview_data.fakeMovieDetailsFirstSectionUiStateError
import com.example.teldatask.presentation.screens.movie_details_screen.preview_data.fakeMovieDetailsFirstSectionUiStateLoading
import com.example.teldatask.presentation.screens.movie_details_screen.preview_data.fakeSimilarMoviesUiState
import com.example.teldatask.presentation.screens.movie_details_screen.preview_data.fakeSimilarMoviesUiStateError
import com.example.teldatask.presentation.screens.movie_details_screen.preview_data.fakeSimilarMoviesUiStateLoading
import com.example.teldatask.presentation.screens.movie_details_screen.ui_state.CreditsUiState
import com.example.teldatask.presentation.screens.movie_details_screen.ui_state.MovieDetailsFirstSectionUiState
import com.example.teldatask.presentation.screens.movie_details_screen.ui_state.SimilarMoviesUiState
import com.example.teldatask.presentation.screens.movie_details_screen.viewmodel.MovieDetailsViewModel
import com.example.teldatask.presentation.ui.theme.TeldaTaskTheme
import com.example.teldatask.presentation.utils.getErrorMessage

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    onBackArrowPressed: () -> Unit
) {
    val movieDetailsViewModel: MovieDetailsViewModel = hiltViewModel()
    LaunchedEffect(key1 = Unit) {
        movieDetailsViewModel.requestMovieDetailsFirstSection(movieId)
        movieDetailsViewModel.requestSimilarMoviesList(movieId)
        movieDetailsViewModel.checkIfFavorite(movieId)
    }
    val movieDetailsFirstSectionUiState by movieDetailsViewModel.movieDetailsFirstSectionUiState.collectAsStateWithLifecycle()
    val similarMoviesUiState by movieDetailsViewModel.similarMoviesUiState.collectAsStateWithLifecycle()
    val creditsUiState by movieDetailsViewModel.creditsUiState.collectAsStateWithLifecycle()
    val isFavourite by movieDetailsViewModel.isFavourite.collectAsStateWithLifecycle()

    MovieDetailsContent(
        movieDetailsUiState = movieDetailsFirstSectionUiState,
        similarMoviesUiState = similarMoviesUiState,
        creditsUiState = creditsUiState,
        onRefreshClicked = {
            movieDetailsViewModel.requestMovieDetailsFirstSection(movieId)
        },
        onBackArrowPressed = onBackArrowPressed,
        isFavoriteMovie = isFavourite,
        onFavouriteButtonClicked = { movieDetailsUiModel ->
            movieDetailsViewModel.toggleFavorite(movieDetailsUiModel.copy(isFavourite = isFavourite))
        },
        onRequestTopCredits = {
            movieDetailsViewModel.requestTopCastForMovies(movieIds = it)
        }
    )
}

@Composable
fun MovieDetailsContent(
    movieDetailsUiState: MovieDetailsFirstSectionUiState,
    similarMoviesUiState: SimilarMoviesUiState,
    creditsUiState: CreditsUiState,
    onBackArrowPressed: () -> Unit,
    onFavouriteButtonClicked: (movieDetailsUiModel: MovieDetailsUiModel) -> Unit,
    onRefreshClicked: () -> Unit,
    isFavoriteMovie: Boolean,
    onRequestTopCredits: (ids: List<Int>) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
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

                Box(
                    modifier = Modifier
                        .fillMaxSize()
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
                        isLiked = isFavoriteMovie,
                        onFavouriteButtonClicked = {
                            onFavouriteButtonClicked( movieDetailsUiModel)
                        },
                        onBackArrowPressed = onBackArrowPressed
                    )
                }
            }

        }
        Spacer(Modifier.height(24.dp))
        SimilarMoviesSection(similarMoviesUiState, onRequestTopCredits = onRequestTopCredits)
        CreditsSection(creditsUiState)
    }
}

@Composable
fun ColumnScope.SimilarMoviesSection(
    similarMoviesUiState: SimilarMoviesUiState,
    onRequestTopCredits:(ids: List<Int>) -> Unit
) {
    Text(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        text = stringResource(R.string.similar_movies),
        style = MaterialTheme.typography.titleMedium,
    )
    when (similarMoviesUiState) {
        is SimilarMoviesUiState.Loading -> {
            if (similarMoviesUiState.isLoading)
                AnimateShimmerSimilarMovies()
        }

        is SimilarMoviesUiState.Error -> {
            val errorMessage = getErrorMessage(
                apiError = similarMoviesUiState.customApiErrorExceptionUiModel,
                databaseError = similarMoviesUiState.customDatabaseExceptionUiModel
            ) ?: stringResource(id = R.string.unknown_exception_message)

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = errorMessage
            )
        }

        is SimilarMoviesUiState.SimilarMoviesList -> {
            onRequestTopCredits(similarMoviesUiState.similarMovies.map { it.id })
            LazyRow(
                Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
            ) {
                items(similarMoviesUiState.similarMovies) { similarMovie ->
                    SimilarMovieItem(
                        movieUiModel = similarMovie,
                        onMovieClick = {}
                    )
                }
            }
        }
    }
}

@Composable
fun ColumnScope.CreditsSection(creditsUiState: CreditsUiState) {
    when(creditsUiState){
        is CreditsUiState.Loading -> {
            if (creditsUiState.isLoading)
                AnimateShimmerSimilarMovies()
        }
        is CreditsUiState.Error -> {
            val errorMessage = getErrorMessage(
                apiError = creditsUiState.customApiErrorExceptionUiModel,
                databaseError = creditsUiState.customDatabaseExceptionUiModel
            ) ?: stringResource(id = R.string.unknown_exception_message)

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp).padding(top = 16.dp),
                text = stringResource(R.string.similar_movies_actors),
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = errorMessage
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp).padding(top = 16.dp),
                text = stringResource(R.string.similar_movies_directors),
                style = MaterialTheme.typography.titleMedium,
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = errorMessage
            )
        }
        is CreditsUiState.Credits -> {
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                text = stringResource(R.string.similar_movies_actors),
                style = MaterialTheme.typography.titleMedium,
            )
            LazyRow(
                Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
            ) {
                items(creditsUiState.creditsUiModel.actors) { actor ->
                    CastItem(
                        profilePath = actor.profilePath,
                        name = actor.name
                    )
                }
            }

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                text = stringResource(R.string.similar_movies_directors),
                style = MaterialTheme.typography.titleMedium,
            )
            LazyRow(
                Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth()
            ) {
                items(creditsUiState.creditsUiModel.directors) { director ->
                    CastItem(
                        profilePath = director.profilePath,
                        name = director.name
                    )
                }
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
            similarMoviesUiState = fakeSimilarMoviesUiState,
            creditsUiState = fakeCreditsUiState,
            onBackArrowPressed = {},
            onFavouriteButtonClicked = { _ -> },
            {},
            isFavoriteMovie = false,
            onRequestTopCredits = {}
        )
    }
}

@Preview
@Composable
fun FilmDetailsScreenLoadingPreview() {
    TeldaTaskTheme {
        MovieDetailsContent(
            movieDetailsUiState = fakeMovieDetailsFirstSectionUiStateLoading,
            similarMoviesUiState = fakeSimilarMoviesUiStateLoading,
            creditsUiState = fakeCreditsUiStateLoading,
            onBackArrowPressed = {},
            onFavouriteButtonClicked = { _ -> },
            {},
            isFavoriteMovie = false,
            onRequestTopCredits = {}
        )
    }
}

@Preview
@Composable
fun FilmDetailsScreenErrorPreview() {
    TeldaTaskTheme {
        MovieDetailsContent(
            movieDetailsUiState = fakeMovieDetailsFirstSectionUiStateError,
            similarMoviesUiState = fakeSimilarMoviesUiStateError,
            creditsUiState = fakeCreditsUiStateError,
            onBackArrowPressed = {},
            onFavouriteButtonClicked = { _ -> },
            {},
            isFavoriteMovie = false,
            onRequestTopCredits = {}
        )
    }
}