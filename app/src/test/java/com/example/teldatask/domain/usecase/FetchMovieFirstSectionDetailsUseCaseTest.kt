package com.example.teldatask.domain.usecase

import com.example.teldatask.domain.fake_data.fakeMovieDetailsDomainModelTest
import com.example.teldatask.domain.model.CustomApiExceptionDomainModel
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
class FetchMovieFirstSectionDetailsUseCaseTest {

    private lateinit var movieDetailsRepository: MovieDetailsRepository
    // CUT
    private lateinit var fetchMovieFirstSectionDetailsUseCase: FetchMovieFirstSectionDetailsUseCase

    @Before
    fun setup() {
        movieDetailsRepository = mockk()
        fetchMovieFirstSectionDetailsUseCase = FetchMovieFirstSectionDetailsUseCase(movieDetailsRepository)
    }

    /**
     * Test Case: Fetch Movie First Section Details Success
     * Given: A movie ID.
     * When: `invoke` is called on FetchMovieFirstSectionDetailsUseCase with the movie ID.
     * Then: It should return the expected MovieDetailsDomainModel from the repository.
     */
    @Test
    fun `invoke, given movie ID, when called, then should return MovieDetailsDomainModel from repository`() = runTest {
        // Given
        val movieId = 123
        val expectedMovieDetailsDomainModel = fakeMovieDetailsDomainModelTest
        coEvery { movieDetailsRepository.fetchMovieDetailsFirstSection(movieId) } returns expectedMovieDetailsDomainModel

        // When
        val result = fetchMovieFirstSectionDetailsUseCase(movieId)

        // Then
        assertEquals(expectedMovieDetailsDomainModel, result)
        coVerify(exactly = 1) { movieDetailsRepository.fetchMovieDetailsFirstSection(movieId) }
    }

    /**
     * Test Case: Fetch Movie First Section Details Failure
     * Given: A movie ID.
     * When: Repository throws an exception during fetch.
     * Then: It should propagate the exception as is.
     */
    @Test(expected = CustomExceptionDomainModel.Api::class)
    fun `invoke, when repository throws exception, then should throw same exception`() = runTest {
        // Given
        val movieId = 123
        val exception = CustomExceptionDomainModel.Api(CustomApiExceptionDomainModel.NetworkExceptionDomainModel)
        coEvery { movieDetailsRepository.fetchMovieDetailsFirstSection(movieId) } throws exception

        // When
        fetchMovieFirstSectionDetailsUseCase(movieId)
    }
}
