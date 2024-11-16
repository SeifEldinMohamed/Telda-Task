package com.example.teldatask.domain.usecase


import com.example.teldatask.domain.model.CustomDatabaseExceptionDomainModel
import com.example.teldatask.domain.model.CustomExceptionDomainModel
import com.example.teldatask.domain.repository.MovieDetailsRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CheckFavouriteMovieUseCaseTest {

    // Mocking MovieDetailsRepository
    private lateinit var movieDetailsRepository: MovieDetailsRepository
    // CUT
    private lateinit var checkFavouriteMovieUseCase: CheckFavouriteMovieUseCase

    @Before
    fun setup() {
        movieDetailsRepository = mockk()
        checkFavouriteMovieUseCase = CheckFavouriteMovieUseCase(movieDetailsRepository)
    }

    /**
     * Test Case: Check Favourite Movie - Success
     * Given: A movie ID.
     * When: `invoke` is called on CheckFavouriteMovieUseCase with the movie ID.
     * Then: It should return the correct favourite status from the repository.
     */
    @Test
    fun `invoke, given movie ID, when called, then should return favourite status from repository`() = runTest {
        // Given
        val movieId = 456
        val expectedFavouriteStatus = true
        coEvery { movieDetailsRepository.isFavorite(movieId) } returns expectedFavouriteStatus

        // When
        val result = checkFavouriteMovieUseCase(movieId)

        // Then
        assertEquals(expectedFavouriteStatus, result)
        coVerify(exactly = 1) { movieDetailsRepository.isFavorite(movieId) }
    }

    /**
     * Test Case: Check Favourite Movie - Failure
     * Given: A movie ID.
     * When: Repository throws an exception during the favourite check.
     * Then: It should propagate the exception as is.
     */
    @Test(expected = CustomExceptionDomainModel.Database::class)
    fun `invoke, when repository throws exception, then should throw same exception`() = runTest {
        // Given
        val movieId = 456
        val exception = CustomExceptionDomainModel.Database(CustomDatabaseExceptionDomainModel.DatabaseFullExceptionDomainModel)
        coEvery { movieDetailsRepository.isFavorite(movieId) } throws exception

        // When
        checkFavouriteMovieUseCase(movieId)
    }
}
