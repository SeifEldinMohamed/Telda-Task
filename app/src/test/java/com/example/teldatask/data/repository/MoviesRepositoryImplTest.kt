package com.example.teldatask.data.repository

import com.example.teldatask.data.data_sources.local.MoviesLocalDataSource
import com.example.teldatask.data.data_sources.remote.MoviesRemoteDataSource
import com.example.teldatask.data.fake_data.fakeFavouriteMoviesListTest
import com.example.teldatask.data.fake_data.fakeMoviesResponseTest
import com.example.teldatask.domain.model.CustomApiExceptionDomainModel
import com.example.teldatask.domain.model.CustomExceptionDomainModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MoviesRepositoryImplTest {

    private lateinit var moviesRemoteDataSource: MoviesRemoteDataSource
    private lateinit var moviesLocalDataSource: MoviesLocalDataSource

    // CUT
    private lateinit var moviesRepository: MoviesRepositoryImpl

    @Before
    fun setup() {
        moviesRemoteDataSource = mockk()
        moviesLocalDataSource = mockk()
        moviesRepository = MoviesRepositoryImpl(
            moviesRemoteDataSource = moviesRemoteDataSource,
            moviesLocalDataSource = moviesLocalDataSource
        )
    }

    /**
     * Test Case: Fetch Popular Movies - Success
     * Given: Remote and local data sources return popular movies and favorite movies respectively.
     * When: `fetchPopularMovies` is called.
     * Then: It should return a list of movies with correct favourite status.
     */
    @Test
    fun `fetchPopularMovies, when called, then should return movies with favourite status`() =
        runTest {
            // Given
            val apiMovies = fakeMoviesResponseTest.movieListDataModel
            val favoriteMovies = fakeFavouriteMoviesListTest

            coEvery { moviesRemoteDataSource.fetchPopularMovies() } returns apiMovies
            every { moviesLocalDataSource.getAllFavoriteMovies() } returns flowOf(favoriteMovies)

            // When
            val result = moviesRepository.fetchPopularMovies().first()

            // Then
            assertEquals(apiMovies.size, result.size)
            result.forEach { movie ->
                val isFavourite = favoriteMovies.any { it.id == movie.id }
                assertEquals(isFavourite, movie.isFavourite)
            }
            coVerify(exactly = 1) { moviesRemoteDataSource.fetchPopularMovies() }
            coVerify(exactly = 1) { moviesLocalDataSource.getAllFavoriteMovies() }
        }

    /**
     * Test Case: Fetch Popular Movies - Exception
     * Given: Remote data source throws an exception.
     * When: `fetchPopularMovies` is called.
     * Then: It should throw the same exception.
     */
    @Test(expected = CustomExceptionDomainModel.Api::class)
    fun `fetchPopularMovies, when remote source throws exception, then should propagate exception`() =
        runTest {
            // Given
            val exception =
                CustomExceptionDomainModel.Api(CustomApiExceptionDomainModel.NetworkExceptionDomainModel)
            coEvery { moviesRemoteDataSource.fetchPopularMovies() } throws exception
            coEvery { moviesLocalDataSource.getAllFavoriteMovies() } returns flowOf(fakeFavouriteMoviesListTest)

            // When
            moviesRepository.fetchPopularMovies().first()
        }

    /**
     * Test Case: Search Movies - Success
     * Given: A query and remote/local data sources returning search results and favorite movies respectively.
     * When: `searchMovies` is called with the query.
     * Then: It should return a list of movies with correct favourite status.
     */
    @Test
    fun `searchMovies, given query, when called, then should return search results with favourite status`() =
        runTest {
            // Given
            val query = "Deadpool"
            val searchMoviesFromApi = fakeMoviesResponseTest.movieListDataModel
            val favoriteMovies = fakeFavouriteMoviesListTest

            coEvery { moviesRemoteDataSource.searchMovies(any()) } returns searchMoviesFromApi
            every { moviesLocalDataSource.getAllFavoriteMovies() } returns flowOf(favoriteMovies)

            // When
            val result = moviesRepository.searchMovies(query).first()

            // Then
            assertEquals(searchMoviesFromApi.size, result.size)
            result.forEach { movie ->
                val isFavourite = favoriteMovies.any { it.id == movie.id }
                assertEquals(isFavourite, movie.isFavourite)
            }
            coVerify(exactly = 1) { moviesRemoteDataSource.searchMovies(query) }
            coVerify(exactly = 1) { moviesLocalDataSource.getAllFavoriteMovies() }
        }

    /**
     * Test Case: Search Movies - Exception
     * Given: Remote data source throws an exception.
     * When: `searchMovies` is called with the query.
     * Then: It should propagate the exception.
     */
    @Test(expected = CustomApiExceptionDomainModel.NetworkExceptionDomainModel::class)
    fun `searchMovies, when remote source throws exception, then should propagate exception`() =
        runTest {
            // Given
            val query = "Deadpool"
            val exception = CustomApiExceptionDomainModel.NetworkExceptionDomainModel
            coEvery { moviesRemoteDataSource.searchMovies(any()) } throws exception

            // When
            moviesRepository.searchMovies(query).first()
        }
}
