package com.example.teldatask.domain.usecase

import com.example.teldatask.domain.model.CustomDatabaseExceptionDomainModel
import com.example.teldatask.domain.model.CustomExceptionDomainModel
import com.example.teldatask.domain.repository.MovieDetailsRepository
import com.example.teldatask.presentation.screens.movie_details_screen.preview_data.fakeMovieDetailsUiModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ToggleFavouriteStatusUseCaseTest {

    // Mocking MovieDetailsRepository
    private lateinit var movieDetailsRepository: MovieDetailsRepository
    // CUT
    private lateinit var toggleFavouriteStatusUseCase: ToggleFavouriteStatusUseCase

    @Before
    fun setup() {
        movieDetailsRepository = mockk()
        toggleFavouriteStatusUseCase = ToggleFavouriteStatusUseCase(movieDetailsRepository)
    }

    /**
     * Test Case: Toggle Favourite Status
     * Given: A MovieDetailsUiModel object.
     * When: `invoke` is called on ToggleFavouriteStatusUseCase with the movie as input.
     * Then: It should call `toggleFavoriteStatus` only one time on the repository with the correct movie object.
     */
    @Test
    fun `invoke, given MovieDetailsUiModel, when toggling favourite status, then should call repository to toggle status`() {
        runTest {
            // Given
            val movie = fakeMovieDetailsUiModel
            coEvery { movieDetailsRepository.toggleFavoriteStatus(movie) } returns Unit

            // When
            toggleFavouriteStatusUseCase(movie)

            // Then
            coVerify(exactly = 1) { movieDetailsRepository.toggleFavoriteStatus(movie) }
        }
    }

    /**
     * Test Case: Toggle Favourite Status Failure
     * Given: A MovieDetailsUiModel object.
     * When: Repository throws an exception during toggle.
     * Then: It should propagate the exception as is.
     */
    @Test(expected = CustomExceptionDomainModel.Database::class)
    fun `invoke, when repository throws exception, then should throw same exception`() = runTest {
        // Given
        val movie = fakeMovieDetailsUiModel
        val exception = CustomExceptionDomainModel.Database(CustomDatabaseExceptionDomainModel.DatabaseFullExceptionDomainModel)
        coEvery { movieDetailsRepository.toggleFavoriteStatus(movie) } throws exception

        // When
        toggleFavouriteStatusUseCase(movie)
    }
}