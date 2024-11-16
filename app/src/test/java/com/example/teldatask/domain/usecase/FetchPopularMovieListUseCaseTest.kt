package com.example.teldatask.domain.usecase

import com.example.teldatask.domain.fake_data.fakePopularMovieListDomainModelTest
import com.example.teldatask.domain.model.CustomApiExceptionDomainModel
import com.example.teldatask.domain.model.CustomDatabaseExceptionDomainModel
import com.example.teldatask.domain.repository.MoviesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class FetchPopularMovieListUseCaseTest {

    private lateinit var moviesRepositoryImpl: MoviesRepository
    private lateinit var fetchPopularMovieListUseCase: FetchPopularMovieListUseCase

    @Before
    fun setup() {
        moviesRepositoryImpl = mockk()
        fetchPopularMovieListUseCase = FetchPopularMovieListUseCase(moviesRepositoryImpl)
    }

    /**
     * Test Case: Success Scenario
     * Given: `fetchPopularMovies()` is called from the repository.
     * When: The repository returns a list of popular movies as a flow.
     * Then: The use case should return the same list of popular movies from the repository.
     */
    @Test
    fun `given successful repository call, when fetching popular movies, then should return repositoryDetailsDomainModel`() {
        runBlocking {
            // Given
            val expected = flowOf(fakePopularMovieListDomainModelTest)
            coEvery { moviesRepositoryImpl.fetchPopularMovies() } returns expected

            // When
            val result = fetchPopularMovieListUseCase()

            // Then
            assertEquals(expected.first(), result.first())
        }
    }

    /**
         * Test Case: Network Exception Scenario
         * Given: `fetchPopularMovies()` is called from the repository.
         * When: The repository throws a `NetworkExceptionDomainModel`.
         * Then: The use case should propagate the same `NetworkExceptionDomainModel`.
     */
    @Test(expected = CustomApiExceptionDomainModel.NetworkExceptionDomainModel::class)
    fun `given repository throws NetworkExceptionDomainModel, when fetching popular movies, then should throw the same exception`() {
        runBlocking {
            // Given
            coEvery { moviesRepositoryImpl.fetchPopularMovies() } throws CustomApiExceptionDomainModel.NetworkExceptionDomainModel

            // When
            fetchPopularMovieListUseCase()
        }
    }

    /**
     * Test Case: Database Full Exception Scenario
     * Given: `fetchPopularMovies()` is called from the repository.
     * When: The repository throws a `DatabaseFullExceptionDomainModel`.
     * Then: The use case should propagate the same `DatabaseFullExceptionDomainModel`.
     */
    @Test(expected = CustomDatabaseExceptionDomainModel.DatabaseFullExceptionDomainModel::class)
    fun `given repository throws DatabaseFullExceptionDomainModel, when fetching popular movies, then should throw the same exception`() {
        runBlocking {
            // Given
            coEvery { moviesRepositoryImpl.fetchPopularMovies() } throws CustomDatabaseExceptionDomainModel.DatabaseFullExceptionDomainModel

            // When
            fetchPopularMovieListUseCase()
        }
    }
}