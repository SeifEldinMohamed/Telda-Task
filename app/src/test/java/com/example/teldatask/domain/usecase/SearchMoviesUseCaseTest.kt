package com.example.teldatask.domain.usecase

import com.example.teldatask.data.repository.MoviesRepositoryImpl
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

class FetchSearchMoviesUseCaseTest {

    private lateinit var moviesRepositoryImpl: MoviesRepository
    private lateinit var searchMoviesUseCase: SearchMoviesUseCase

    @Before
    fun setup() {
        moviesRepositoryImpl = mockk()
        searchMoviesUseCase = SearchMoviesUseCase(moviesRepositoryImpl)
    }

    /**
     * Test Case: Success Scenario
     * Given: `searchMovies()` is called with a query string.
     * When: The repository returns a list of searched movies as a flow.
     * Then: The use case should return the same list of searched movies from the repository.
     */
    @Test
    fun `given successful repository call, when searching for movies, then should return repositoryDetailsDomainModel`() =
        runBlocking {
            // Given
            val query = "Deadpool"
            val expected = flowOf(fakePopularMovieListDomainModelTest)
            coEvery { moviesRepositoryImpl.searchMovies(query) } returns expected

            // When
            val result = searchMoviesUseCase(query)

            // Then
            assertEquals(expected.first(), result.first())
        }

    /**
     * Test Case: Network Exception Scenario
     * Given: `searchMovies()` is called with a query string.
     * When: The repository throws a `NetworkExceptionDomainModel`.
     * Then: The use case should propagate the same `NetworkExceptionDomainModel`.
     */
    @Test(expected = CustomApiExceptionDomainModel.NetworkExceptionDomainModel::class)
    fun `given repository throws NetworkExceptionDomainModel, when searching for movies, then should throw the same exception`() {
        runBlocking {
            // Given
            val query = "Deadpool"
            val customException = CustomApiExceptionDomainModel.NetworkExceptionDomainModel
            coEvery { moviesRepositoryImpl.searchMovies(query) } throws customException

            // When
            searchMoviesUseCase(query)
        }
    }

    /**
     * Test Case: Database Full Exception Scenario
     * Given: `searchMovies()` is called with a query string.
     * When: The repository throws a `DatabaseFullExceptionDomainModel`.
     * Then: The use case should propagate the same `DatabaseFullExceptionDomainModel`.
     */
    @Test(expected = CustomDatabaseExceptionDomainModel.DatabaseFullExceptionDomainModel::class)
    fun `given repository throws DatabaseFullExceptionDomainModel, when searching for movies, then should throw the same exception`() {
        runBlocking {
            // Given
            val query = "Deadpool"
            val customException = CustomDatabaseExceptionDomainModel.DatabaseFullExceptionDomainModel
            coEvery { moviesRepositoryImpl.searchMovies(query) } throws customException

            // When
            searchMoviesUseCase(query)
        }
    }
}
