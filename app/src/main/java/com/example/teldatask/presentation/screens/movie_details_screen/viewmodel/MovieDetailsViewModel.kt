package com.example.teldatask.presentation.screens.movie_details_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teldatask.domain.model.CustomExceptionDomainModel
import com.example.teldatask.domain.usecase.CheckFavouriteMovieUseCase
import com.example.teldatask.domain.usecase.FetchMovieFirstSectionDetailsUseCase
import com.example.teldatask.domain.usecase.FetchSimilarMoviesListUseCase
import com.example.teldatask.domain.usecase.FetchTopCastUseCase
import com.example.teldatask.domain.usecase.ToggleFavouriteStatusUseCase
import com.example.teldatask.presentation.mapper.toCreditsUiModel
import com.example.teldatask.presentation.mapper.toCustomApiExceptionUiModel
import com.example.teldatask.presentation.mapper.toMovieDetailsUIModel
import com.example.teldatask.presentation.mapper.toMovieUIModel
import com.example.teldatask.presentation.screens.movie_details_screen.model.MovieDetailsUiModel
import com.example.teldatask.presentation.screens.movie_details_screen.ui_state.CreditsUiState
import com.example.teldatask.presentation.screens.movie_details_screen.ui_state.MovieDetailsFirstSectionUiState
import com.example.teldatask.presentation.screens.movie_details_screen.ui_state.SimilarMoviesUiState
import com.example.teldatask.presentation.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val fetchMovieDetailsFirstSectionUseCase: FetchMovieFirstSectionDetailsUseCase,
    private val fetchSimilarMoviesListUseCase: FetchSimilarMoviesListUseCase,
    private val fetchTopCastUseCase: FetchTopCastUseCase,
    private val toggleFavouriteStatusUseCase: ToggleFavouriteStatusUseCase,
    private val checkFavouriteMovieUseCase: CheckFavouriteMovieUseCase,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val _movieDetailsFirstSectionUiState: MutableStateFlow<MovieDetailsFirstSectionUiState> = MutableStateFlow(
        MovieDetailsFirstSectionUiState.Loading(isLoading = false))
    val movieDetailsFirstSectionUiState = _movieDetailsFirstSectionUiState.asStateFlow()

    private val _similarMoviesUiState: MutableStateFlow<SimilarMoviesUiState> = MutableStateFlow(
        SimilarMoviesUiState.Loading(isLoading = false))
    val similarMoviesUiState = _similarMoviesUiState.asStateFlow()

    private val _creditsUiState = MutableStateFlow<CreditsUiState>(CreditsUiState.Loading(isLoading = false))
    val creditsUiState: StateFlow<CreditsUiState> = _creditsUiState.asStateFlow()

    private val _isFavourite = MutableStateFlow(false)
    val isFavourite: StateFlow<Boolean> = _isFavourite.asStateFlow()

    fun requestMovieDetailsFirstSection(movieId: Int) {
        _movieDetailsFirstSectionUiState.value = MovieDetailsFirstSectionUiState.Loading(isLoading = true)
        viewModelScope.launch(dispatcher.io) {
            delay(1000L) // to see shimmer animation
            try {
                val movieDetailsDomainModel = fetchMovieDetailsFirstSectionUseCase(movieId)
                _movieDetailsFirstSectionUiState.value = MovieDetailsFirstSectionUiState.MovieDetailsFirstSection(
                        movieDetailsUiModel = movieDetailsDomainModel.toMovieDetailsUIModel()
                    )

            } catch (e: Exception) {
                _movieDetailsFirstSectionUiState.value = MovieDetailsFirstSectionUiState.Error(
                    customApiErrorExceptionUiModel = (e as CustomExceptionDomainModel.Api).apiException.toCustomApiExceptionUiModel()
                )
            }
        }
    }

    fun requestSimilarMoviesList(movieId: Int) {
        _similarMoviesUiState.value = SimilarMoviesUiState.Loading(isLoading = true)
        viewModelScope.launch(dispatcher.io) {
            delay(1000L) // to see shimmer animation
            try {
                val similarMoviesDomainModel = fetchSimilarMoviesListUseCase(movieId)
                _similarMoviesUiState.value = SimilarMoviesUiState.SimilarMoviesList(
                    similarMovies = similarMoviesDomainModel.map { it.toMovieUIModel() }
                )

            } catch (e: Exception) {
                _similarMoviesUiState.value = SimilarMoviesUiState.Error(
                    customApiErrorExceptionUiModel = (e as CustomExceptionDomainModel.Api).apiException.toCustomApiExceptionUiModel()
                )
            }
        }
    }

    fun requestTopCastForMovies(movieIds: List<Int>) {
        _creditsUiState.value = CreditsUiState.Loading(isLoading = true)
        viewModelScope.launch(dispatcher.io) {
            try {
                val creditsDirection = fetchTopCastUseCase(movieIds)
                _creditsUiState.value =  CreditsUiState.Credits(creditsUiModel = creditsDirection.toCreditsUiModel())
            } catch (e: Exception){
                _creditsUiState.value = CreditsUiState.Error(
                    customApiErrorExceptionUiModel = (e as CustomExceptionDomainModel.Api).apiException.toCustomApiExceptionUiModel()
                )
            }
        }
    }

    fun checkIfFavorite(movieId: Int) {
        viewModelScope.launch(dispatcher.io) {
            _isFavourite.value = checkFavouriteMovieUseCase(movieId)
        }
    }

    fun toggleFavorite(movie: MovieDetailsUiModel) {
        viewModelScope.launch(dispatcher.io) {
            toggleFavouriteStatusUseCase(movie)
            _isFavourite.value = !_isFavourite.value
        }
    }
}