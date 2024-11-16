package com.example.teldatask.domain.usecase

import com.example.teldatask.domain.fake_data.fakePopularMovieListDomainModelTest
import com.example.teldatask.domain.model.CustomApiExceptionDomainModel
import com.example.teldatask.domain.model.CustomExceptionDomainModel
import com.example.teldatask.domain.repository.MovieDetailsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchSimilarMoviesUseCaseTest {

    private lateinit var movieDetailsRepository: MovieDetailsRepository
    private lateinit var fetchSimilarMoviesListUseCase: FetchSimilarMoviesListUseCase

    @Before
    fun setup() {
        movieDetailsRepository = mockk()
        fetchSimilarMoviesListUseCase = FetchSimilarMoviesListUseCase(movieDetailsRepository)
    }

    /**
     * Test Case: Success Scenario
     * Given: `fetchPopularMovies()` is called from the repository.
     * When: The repository returns a list of popular movies as a flow.
     * Then: The use case should return the same list of popular movies from the repository.
     */
    @Test
    fun `given movieIds, when fetchTopCastUseCase is called, then it should return top actors and directors`() = runTest {
        // Given
        val expected = fakePopularMovieListDomainModelTest
        coEvery { movieDetailsRepository.fetchSimilarMovies(any()) } returns expected

        // When
        val result = fetchSimilarMoviesListUseCase(1)

        // Then
        assertEquals(expected, result)
    }

    /**
     * Test Case: Network Exception Scenario
     * Given: `fetchSimilarMoviesListUseCase()` is called from the repository.
     * When: The repository throws a `NetworkExceptionDomainModel`.
     * Then: The use case should propagate the same `NetworkExceptionDomainModel`.
     */
    @Test(expected = CustomExceptionDomainModel.Api::class)
    fun `given repository throws NetworkExceptionDomainModel, when fetching popular movies, then should throw the same exception`() = runTest {
        // Given
        coEvery { movieDetailsRepository.fetchSimilarMovies(any()) } throws CustomExceptionDomainModel.Api(CustomApiExceptionDomainModel.NetworkExceptionDomainModel)

        // When
        fetchSimilarMoviesListUseCase(1)
    }
}
