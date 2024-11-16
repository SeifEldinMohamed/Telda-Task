package com.example.teldatask.presentation.screens.movies_home_screen

import app.cash.turbine.test
import com.example.teldatask.domain.model.CustomApiExceptionDomainModel
import com.example.teldatask.domain.usecase.FetchPopularMovieListUseCase
import com.example.teldatask.domain.usecase.SearchMoviesUseCase
import com.example.teldatask.domain.fake_data.fakePopularMovieListDomainModelTest
import com.example.teldatask.domain.fake_data.fakeSearchedMovieListDomainModelTest
import com.example.teldatask.presentation.fake_data.fakePopularMovieListUiModelTest
import com.example.teldatask.presentation.mapper.toCustomApiExceptionUiModel
import com.example.teldatask.presentation.mapper.toMovieUIModel
import com.example.teldatask.presentation.utils.MainCoroutineRule
import com.example.teldatask.presentation.utils.TestDispatchersImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MoviesHomeViewModelTest {

    private lateinit var moviesHomeViewModel: MoviesHomeViewModel
    private lateinit var fetchPopularMovieListUseCase: FetchPopularMovieListUseCase
    private lateinit var searchMoviesUseCase: SearchMoviesUseCase

    private lateinit var testDispatcher: TestDispatchersImpl

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    @Before
    fun setup() {
        testDispatcher = TestDispatchersImpl()
        fetchPopularMovieListUseCase = mockk(relaxed = true)
        searchMoviesUseCase = mockk(relaxed = true)
        moviesHomeViewModel = MoviesHomeViewModel(
            fetchPopularMovieListUseCase,
            searchMoviesUseCase,
            testDispatcher
        )
    }

    /**
     * Test Case: requestPopularMovies
     * Given: Successful call to fetch popular movie list use case.
     * When: Requesting popular movies.
     * Then: It should emit a loading state first, followed by the popular movies list state.
     */
    @Test
    fun `requestPopularMovies, given successful call, when requesting popular movies, then should emit loading then PopularMoviesList`() = runTest {
        // Given
        val popularMovieListDomainModel = fakePopularMovieListDomainModelTest
        val expected = popularMovieListDomainModel.map { it.toMovieUIModel() }
        coEvery { fetchPopularMovieListUseCase() } returns flowOf(popularMovieListDomainModel)

        // When
        moviesHomeViewModel.requestPopularMovies()

        // Then
        moviesHomeViewModel.moviesHomeUiState.test {
            assertEquals(MoviesHomeUiState.Loading(isLoading = true), awaitItem())
            advanceUntilIdle()
            assertEquals(
                MoviesHomeUiState.PopularMoviesList(popularMoviesList = expected),
                awaitItem()
            )
            cancelAndConsumeRemainingEvents()
        }
    }

    /**
     * Test Case: requestPopularMovies should emit error state on exception
     * Given: Exception thrown from fetch popular movie list use case.
     * When: Requesting popular movies.
     * Then: It should emit a loading state first, followed by an API error state.
     */
    @Test
    fun `requestPopularMovies, given exception, when requesting popular movies, then should emit error state`() = runTest {
        // Given
        val exception = CustomApiExceptionDomainModel.UnknownExceptionDomainModel
        coEvery { fetchPopularMovieListUseCase() } throws exception

        // When
        moviesHomeViewModel.requestPopularMovies()

        // Then
        moviesHomeViewModel.moviesHomeUiState.test {
            assertEquals(MoviesHomeUiState.Loading(isLoading = true), awaitItem())
            advanceUntilIdle()
            assertEquals(
                MoviesHomeUiState.ApiError(exception.toCustomApiExceptionUiModel()),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    /**
     * Test Case: onSearchQueryChanged should update search query
     * Given: A search query string.
     * When: Changing the search query.
     * Then: It should update the search query state with the new value.
     */
    @Test
    fun `onSearchQueryChanged, given search query, when updating query, then should update search query state`() = runTest {
        // Given
        val searchQuery = "Deadpool"

        // When
        moviesHomeViewModel.onSearchQueryChanged(searchQuery)

        // Then
        assertEquals(searchQuery, moviesHomeViewModel.searchQuery.value)
    }

    /**
     * Test Case: observeSearchQuery should emit searched movie list when query is not empty
     * Given: A non-empty search query with results from the use case.
     * When: Observing the search query changes.
     * Then: It should emit the searched movies list.
     */
    @Test
    fun `observeSearchQuery, given non-empty query, when observing search query, then should emit searched movies list`() = runTest {
        // Given
        val searchQuery = "Deadpool"
        val searchedMovies = fakeSearchedMovieListDomainModelTest
        val expected = searchedMovies.map { it.toMovieUIModel() }
        coEvery { searchMoviesUseCase(searchQuery) } returns flowOf(searchedMovies)

        // When
        moviesHomeViewModel.onSearchQueryChanged(searchQuery)
        moviesHomeViewModel.observeSearchQuery()
        advanceUntilIdle()

        // Then
        moviesHomeViewModel.moviesHomeUiState.test {
            assertEquals(
                MoviesHomeUiState.SearchedMoviesList(searchedMoviesList = expected),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    /**
     * Test Case: observeSearchQuery should emit empty state when no search results
     * Given: A search query with no matching results.
     * When: Observing the search query changes.
     * Then: It should emit an empty state.
     */
    @Test
    fun `observeSearchQuery, given no results, when observing search query, then should emit empty state`() = runTest {
        // Given
        val searchQuery = "NonExistingMovie"
        coEvery { searchMoviesUseCase(searchQuery) } returns flowOf(emptyList())

        // When
        moviesHomeViewModel.onSearchQueryChanged(searchQuery)
        moviesHomeViewModel.observeSearchQuery()
        advanceUntilIdle()

        // Then
        moviesHomeViewModel.moviesHomeUiState.test {
            assertEquals(MoviesHomeUiState.EmptyState, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    /**
     * Test Case: observeSearchQuery should reset to popular movies list when query is empty
     * Given: An empty search query.
     * When: Observing the search query changes.
     * Then: It should reset to the original popular movies list.
     */
    @Test
    fun `observeSearchQuery, given empty query, when observing search query, then should reset to popular movies list`() = runTest {
        // Given
        val searchQuery = ""
        val popularMovieListUiModel = fakePopularMovieListUiModelTest
        moviesHomeViewModel.originalPopularMoviesList = popularMovieListUiModel

        // When
        moviesHomeViewModel.observeSearchQuery()
        moviesHomeViewModel.onSearchQueryChanged(searchQuery)
        advanceUntilIdle()

        // Then
        moviesHomeViewModel.moviesHomeUiState.test {
            assertEquals(
                MoviesHomeUiState.PopularMoviesList(popularMovieListUiModel),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }
}
