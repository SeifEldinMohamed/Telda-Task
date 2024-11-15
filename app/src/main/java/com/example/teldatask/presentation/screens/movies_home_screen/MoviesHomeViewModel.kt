package com.example.teldatask.presentation.screens.movies_home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teldatask.presentation.mapper.toMovieUIModel
import com.example.teldatask.presentation.utils.DispatcherProvider
import com.example.teldatask.domain.model.CustomApiExceptionDomainModel
import com.example.teldatask.domain.usecase.FetchPopularMovieListUseCase
import com.example.teldatask.domain.usecase.SearchMoviesUseCase
import com.example.teldatask.presentation.mapper.toCustomApiExceptionUiModel
import com.example.teldatask.presentation.screens.movies_home_screen.model.MovieUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesHomeViewModel @Inject constructor(
    private val fetchPopularMovieListUseCase: FetchPopularMovieListUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val _moviesHomeUiState: MutableStateFlow<MoviesHomeUiState> =
        MutableStateFlow(MoviesHomeUiState.Loading(isLoading = false))
    val moviesHomeUiState: StateFlow<MoviesHomeUiState> = _moviesHomeUiState.asStateFlow()

    private var originalPopularMoviesList: List<MovieUiModel> = emptyList()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        requestPopularMovies()
        observeSearchQuery()
    }

    private fun requestPopularMovies() {
        _moviesHomeUiState.value = MoviesHomeUiState.Loading(isLoading = true)
        viewModelScope.launch(dispatcher.io) {
            try {
                val result = fetchPopularMovieListUseCase()
                val popularMoviesList = result.map { it.toMovieUIModel() }
                _moviesHomeUiState.value = MoviesHomeUiState.PopularMoviesList(
                    popularMoviesList = popularMoviesList
                )
                originalPopularMoviesList = popularMoviesList

            } catch (e: Exception) {
                _moviesHomeUiState.value = MoviesHomeUiState.ApiError(
                    customApiErrorExceptionUiModel = (e as CustomApiExceptionDomainModel).toCustomApiExceptionUiModel()
                )
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchQuery() {
        viewModelScope.launch(dispatcher.io) {
            _searchQuery
                .debounce(500) // Waits for 500 ms of inactivity
                .distinctUntilChanged() // Ignore duplicate queries
                .collect { query ->
                    if (query.isEmpty()) {
                        _moviesHomeUiState.value =
                            MoviesHomeUiState.PopularMoviesList(popularMoviesList = originalPopularMoviesList)
                    } else {
                        val searchedMovies = searchMoviesUseCase(query = query)
                        if (searchedMovies.isEmpty()){
                            _moviesHomeUiState.value = MoviesHomeUiState.EmptyState
                        }
                        else {
                            _moviesHomeUiState.value = MoviesHomeUiState.SearchedMoviesList(
                                searchedMoviesList = searchedMovies.map { it.toMovieUIModel() }
                            )
                        }
                    }
                }
        }
    }
}