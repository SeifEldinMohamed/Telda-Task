package com.example.teldatask.presentation.screens.movie_details_screen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teldatask.domain.model.CustomApiExceptionDomainModel
import com.example.teldatask.domain.usecase.FetchMovieFirstSectionDetailsUseCase
import com.example.teldatask.domain.usecase.FetchSimilarMoviesListUseCase
import com.example.teldatask.presentation.mapper.toCustomApiExceptionUiModel
import com.example.teldatask.presentation.mapper.toMovieDetailsUIModel
import com.example.teldatask.presentation.mapper.toMovieUIModel
import com.example.teldatask.presentation.screens.movie_details_screen.ui_state.MovieDetailsFirstSectionUiState
import com.example.teldatask.presentation.screens.movie_details_screen.ui_state.SimilarMoviesUiState
import com.example.teldatask.presentation.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val fetchMovieDetailsFirstSectionUseCase: FetchMovieFirstSectionDetailsUseCase,
    private val fetchSimilarMoviesListUseCase: FetchSimilarMoviesListUseCase,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val _movieDetailsFirstSectionUiState: MutableStateFlow<MovieDetailsFirstSectionUiState> = MutableStateFlow(
        MovieDetailsFirstSectionUiState.Loading(isLoading = false))
    val movieDetailsFirstSectionUiState = _movieDetailsFirstSectionUiState.asStateFlow()

    private val _similarMoviesUiState: MutableStateFlow<SimilarMoviesUiState> = MutableStateFlow(
        SimilarMoviesUiState.Loading(isLoading = false))
    val similarMoviesUiState = _similarMoviesUiState.asStateFlow()

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
                    customApiErrorExceptionUiModel = (e as CustomApiExceptionDomainModel).toCustomApiExceptionUiModel()
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
                    customApiErrorExceptionUiModel = (e as CustomApiExceptionDomainModel).toCustomApiExceptionUiModel()
                )
            }
        }
    }
}