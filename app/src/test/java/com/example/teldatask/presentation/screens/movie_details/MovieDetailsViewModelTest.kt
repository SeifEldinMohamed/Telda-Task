package com.example.teldatask.presentation.screens.movie_details

import app.cash.turbine.test
import com.example.teldatask.domain.fake_data.fakeCreditsListDomainModelTest
import com.example.teldatask.domain.fake_data.fakeMovieDetailsDomainModelTest
import com.example.teldatask.domain.fake_data.fakeSimilarMoviesListTest
import com.example.teldatask.domain.usecase.CheckFavouriteMovieUseCase
import com.example.teldatask.domain.usecase.FetchMovieFirstSectionDetailsUseCase
import com.example.teldatask.domain.usecase.FetchSimilarMoviesListUseCase
import com.example.teldatask.domain.usecase.FetchTopCastUseCase
import com.example.teldatask.domain.usecase.ToggleFavouriteStatusUseCase
import com.example.teldatask.presentation.mapper.toCreditsUiModel
import com.example.teldatask.presentation.mapper.toMovieDetailsUIModel
import com.example.teldatask.presentation.mapper.toMovieUIModel
import com.example.teldatask.presentation.screens.movie_details_screen.preview_data.fakeMovieDetailsUiModel
import com.example.teldatask.presentation.screens.movie_details_screen.ui_state.CreditsUiState
import com.example.teldatask.presentation.screens.movie_details_screen.ui_state.MovieDetailsFirstSectionUiState
import com.example.teldatask.presentation.screens.movie_details_screen.ui_state.SimilarMoviesUiState
import com.example.teldatask.presentation.screens.movie_details_screen.viewmodel.MovieDetailsViewModel
import com.example.teldatask.presentation.utils.MainCoroutineRule
import com.example.teldatask.presentation.utils.TestDispatchersImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieDetailsViewModelTest {

    private lateinit var movieDetailsViewModel: MovieDetailsViewModel
    private lateinit var fetchMovieDetailsFirstSectionUseCase: FetchMovieFirstSectionDetailsUseCase
    private lateinit var fetchSimilarMoviesListUseCase: FetchSimilarMoviesListUseCase
    private lateinit var fetchTopCastUseCase: FetchTopCastUseCase
    private lateinit var toggleFavouriteStatusUseCase: ToggleFavouriteStatusUseCase
    private lateinit var checkFavouriteMovieUseCase: CheckFavouriteMovieUseCase
    private lateinit var dispatcher: TestDispatchersImpl

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    @Before
    fun setup() {
        dispatcher = TestDispatchersImpl()
        fetchMovieDetailsFirstSectionUseCase = mockk()
        fetchSimilarMoviesListUseCase = mockk()
        fetchTopCastUseCase = mockk()
        toggleFavouriteStatusUseCase = mockk()
        checkFavouriteMovieUseCase = mockk()

        movieDetailsViewModel = MovieDetailsViewModel(
            fetchMovieDetailsFirstSectionUseCase,
            fetchSimilarMoviesListUseCase,
            fetchTopCastUseCase,
            toggleFavouriteStatusUseCase,
            checkFavouriteMovieUseCase,
            dispatcher
        )
    }

    /**
     * Test Case: Movie Details First Section Request
     * Given: A valid movieId and successful response from the use case.
     * When: `requestMovieDetailsFirstSection()` is called.
     * Then: It should emit a MovieDetailsFirstSection state with the correct data.
     */
    @Test
    fun `requestMovieDetailsFirstSection, given successful use case response, when request is made, then should emit MovieDetailsFirstSection`() = runTest {
        // Given
        val movieId = 1
        val movieDetailsDomainModel = fakeMovieDetailsDomainModelTest
        val expected = MovieDetailsFirstSectionUiState.MovieDetailsFirstSection(
            movieDetailsUiModel = movieDetailsDomainModel.toMovieDetailsUIModel()
        )
        coEvery { fetchMovieDetailsFirstSectionUseCase(movieId) } returns movieDetailsDomainModel

        // When
        movieDetailsViewModel.requestMovieDetailsFirstSection(movieId)

        // Then
        movieDetailsViewModel.movieDetailsFirstSectionUiState.test {
            assertEquals(MovieDetailsFirstSectionUiState.Loading(isLoading = true), awaitItem())
            advanceUntilIdle()
            assertEquals(expected, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    /**
     * Test Case: Similar Movies List Request
     * Given: A valid movieId and successful response from the use case.
     * When: `requestSimilarMoviesList()` is called.
     * Then: It should emit a SimilarMoviesList state with the correct data.
     */
    @Test
    fun `requestSimilarMoviesList, given successful use case response, when request is made, then should emit SimilarMoviesList`() = runTest {
        // Given
        val movieId = 1
        val similarMoviesDomainModel = fakeSimilarMoviesListTest
        val expected = SimilarMoviesUiState.SimilarMoviesList(
            similarMovies = similarMoviesDomainModel.map { it.toMovieUIModel() }
        )
        coEvery { fetchSimilarMoviesListUseCase(movieId) } returns similarMoviesDomainModel

        // When
        movieDetailsViewModel.requestSimilarMoviesList(movieId)

        // Then
        movieDetailsViewModel.similarMoviesUiState.test {
            assertEquals(SimilarMoviesUiState.Loading(isLoading = true), awaitItem())
            advanceUntilIdle()
            assertEquals(expected, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    /**
     * Test Case: Top Cast Request
     * Given: A valid list of movieIds and successful response from the use case.
     * When: `requestTopCastForMovies()` is called.
     * Then: It should emit a Credits state with the correct data.
     */
    @Test
    fun `requestTopCastForMovies, given successful use case response, when request is made, then should emit Credits`() = runTest {
        // Given
        val movieIds = listOf(1, 2)
        val creditsDomainModel = fakeCreditsListDomainModelTest
        val expected = CreditsUiState.Credits(creditsUiModel = creditsDomainModel.toCreditsUiModel())
        coEvery { fetchTopCastUseCase(movieIds) } returns creditsDomainModel

        // When
        movieDetailsViewModel.requestTopCastForMovies(movieIds)

        // Then
        movieDetailsViewModel.creditsUiState.test {
            assertEquals(CreditsUiState.Loading(isLoading = true), awaitItem())
            advanceUntilIdle()
            assertEquals(expected, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    /**
     * Test Case: Toggle Favourite Status
     * Given: A valid movie and current favourite status.
     * When: `toggleFavorite()` is called.
     * Then: It should toggle the favourite status correctly.
     */
    @Test
    fun `toggleFavorite, given current favourite status, when toggled, then should update isFavourite state`() = runTest {
        // Given
        val movie = fakeMovieDetailsUiModel
        val initialFavouriteStatus = false
        coEvery { checkFavouriteMovieUseCase(any()) } returns initialFavouriteStatus
        coEvery { toggleFavouriteStatusUseCase(any()) } returns Unit
        movieDetailsViewModel.checkIfFavorite(movie.id)

        movieDetailsViewModel.toggleFavorite(movie)

        // Then
        movieDetailsViewModel.isFavourite.test {
            assertEquals(initialFavouriteStatus, awaitItem())
            assertEquals(!initialFavouriteStatus, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    /**
     * Test Case: Check If Movie Is Favourite
     * Given: A valid movieId.
     * When: `checkIfFavorite()` is called.
     * Then: It should emit the correct favourite status.
     */
    @Test
    fun `checkIfFavorite, given a valid movieId, when checked, then should emit correct favourite status`() = runTest {
        // Given
        val movieId = 1
        val isFavourite = true
        coEvery { checkFavouriteMovieUseCase(movieId) } returns isFavourite

        // When
        movieDetailsViewModel.checkIfFavorite(movieId)
        advanceUntilIdle()
        // Then
        movieDetailsViewModel.isFavourite.test {
            assertEquals(isFavourite, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }
}

