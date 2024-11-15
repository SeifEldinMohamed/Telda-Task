package com.example.teldatask.presentation.screens.movies_home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teldatask.presentation.mapper.toPopularMovieUIModel
import com.example.teldatask.presentation.utils.DispatcherProvider
import com.example.teldatask.domain.model.CustomApiExceptionDomainModel
import com.example.teldatask.domain.usecase.FetchPopularMovieListUseCase
import com.example.teldatask.presentation.mapper.toCustomApiExceptionUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesHomeViewModel @Inject constructor(
    private val fetchPopularMovieListUseCase: FetchPopularMovieListUseCase,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val _moviesHomeUiState:MutableStateFlow<MoviesHomeUiState> = MutableStateFlow(MoviesHomeUiState.Loading(isLoading = false))
    val moviesHomeUiState get() = _moviesHomeUiState.asStateFlow()

    init {
        requestPopularMovies()
    }

    private fun requestPopularMovies() {
        _moviesHomeUiState.value = MoviesHomeUiState.Loading(isLoading = true)
        viewModelScope.launch(dispatcher.io) {
            try {
                val result = fetchPopularMovieListUseCase()
                _moviesHomeUiState.value = MoviesHomeUiState.PopularMoviesList(
                    popularMoviesList = result.map { it.toPopularMovieUIModel() }
                )

            } catch (e: Exception) {
                _moviesHomeUiState.value = MoviesHomeUiState.ApiError(
                    customApiErrorExceptionUiModel = (e as CustomApiExceptionDomainModel).toCustomApiExceptionUiModel()
                )
            }
        }
    }
}