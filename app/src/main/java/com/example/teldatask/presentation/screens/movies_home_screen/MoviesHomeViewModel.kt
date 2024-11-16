package com.example.teldatask.presentation.screens.movies_home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teldatask.presentation.mapper.toMovieUIModel
import com.example.teldatask.presentation.utils.DispatcherProvider
import com.example.teldatask.domain.model.CustomExceptionDomainModel
import com.example.teldatask.domain.usecase.FetchPopularMovieListUseCase
import com.example.teldatask.domain.usecase.SearchMoviesUseCase
import com.example.teldatask.presentation.mapper.toCustomApiExceptionUiModel
import com.example.teldatask.presentation.mapper.toCustomDatabaseExceptionUiModel
import com.example.teldatask.presentation.model.CustomApiExceptionUiModel
import com.example.teldatask.presentation.screens.movies_home_screen.model.MovieUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
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
                fetchPopularMovieListUseCase().collect { popularMovies ->
                    val popularMoviesList = popularMovies.map { it.toMovieUIModel() }
                    _moviesHomeUiState.value = MoviesHomeUiState.PopularMoviesList(
                        popularMoviesList = popularMoviesList
                    )
                    originalPopularMoviesList = popularMoviesList
                }

            } catch (e: Exception) {
                handleCustomException(e)
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
                .debounce(500)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isEmpty()) {
                        _moviesHomeUiState.value =
                            MoviesHomeUiState.PopularMoviesList(popularMoviesList = originalPopularMoviesList)
                    } else {
                        searchMoviesUseCase(query = query)
                            .catch {
                                handleCustomException(it)
                            }
                            .collect { searchedMovies ->
                                if (searchedMovies.isEmpty()) {
                                    _moviesHomeUiState.value = MoviesHomeUiState.EmptyState
                                } else {
                                    _moviesHomeUiState.value = MoviesHomeUiState.SearchedMoviesList(
                                        searchedMoviesList = searchedMovies.map { it.toMovieUIModel() }
                                    )
                                }
                            }
                    }
                }
        }
    }

    private fun handleCustomException(exception: Throwable) {
        when (val customException = exception as? CustomExceptionDomainModel) {
            is CustomExceptionDomainModel.Api -> {
                val apiExceptionUiModel =
                    customException.apiException.toCustomApiExceptionUiModel()
                _moviesHomeUiState.value = MoviesHomeUiState.ApiError(apiExceptionUiModel)
            }

            is CustomExceptionDomainModel.Database -> {
                val databaseExceptionUiModel =
                    customException.databaseException.toCustomDatabaseExceptionUiModel()
                _moviesHomeUiState.value = MoviesHomeUiState.DatabaseError(databaseExceptionUiModel)
            }

            else -> {
                _moviesHomeUiState.value = MoviesHomeUiState.ApiError(
                    customApiErrorExceptionUiModel = CustomApiExceptionUiModel.Unknown
                )
            }
        }
    }

    fun resetSearchQuery() {
        _searchQuery.value = ""
    }
}

