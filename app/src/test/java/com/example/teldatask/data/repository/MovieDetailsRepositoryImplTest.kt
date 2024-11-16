package com.example.teldatask.data.repository

import com.example.teldatask.data.data_sources.local.MoviesLocalDataSource
import com.example.teldatask.data.data_sources.remote.MoviesRemoteDataSource
import com.example.teldatask.data.fake_data.fakeFavouriteMoviesListTest
import com.example.teldatask.data.fake_data.fakeMovieDetailsResponseTest
import com.example.teldatask.data.fake_data.fakeMoviesResponseTest
import com.example.teldatask.data.mapper.toFavouriteMovieEntity
import com.example.teldatask.data.mapper.toMovieDetailsDomainModel
import com.example.teldatask.data.mapper.toMovieDomainModel
import com.example.teldatask.domain.model.CustomApiExceptionDomainModel
import com.example.teldatask.domain.model.CustomExceptionDomainModel
import com.example.teldatask.presentation.screens.movie_details_screen.preview_data.fakeMovieDetailsUiModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MovieDetailsRepositoryImplTest {

    private lateinit var moviesRemoteDataSource: MoviesRemoteDataSource

    private lateinit var moviesLocalDataSource: MoviesLocalDataSource

    // CUT
    private lateinit var movieDetailsRepositoryImpl: MovieDetailsRepositoryImpl

    @Before
    fun setup() {
        moviesRemoteDataSource = mockk()
        moviesLocalDataSource = mockk()
        movieDetailsRepositoryImpl = MovieDetailsRepositoryImpl(
            moviesRemoteDataSource = moviesRemoteDataSource,
            moviesLocalDataSource = moviesLocalDataSource
        )
    }

    /**
     * Test Case 1: Fetch Movie Details First Section - Success
     */
    @Test
    fun `fetchMovieDetailsFirstSection, given valid movieId, when fetch is successful, then should return MovieDetailsDomainModel`() = runTest {
        // Given
        val movieId = 101
        val movieDetailsResponse = fakeMovieDetailsResponseTest
        val expectedDomainModel = movieDetailsResponse.toMovieDetailsDomainModel()
        coEvery { moviesRemoteDataSource.fetchMovieDetails(movieId) } returns movieDetailsResponse

        // When
        val result = movieDetailsRepositoryImpl.fetchMovieDetailsFirstSection(movieId)

        // Then
        assertEquals(expectedDomainModel, result)
    }

    /**
     * Test Case 2: Fetch Movie Details First Section - Failure
     */
    @Test(expected = CustomExceptionDomainModel.Api::class)
    fun `fetchMovieDetailsFirstSection, given valid movieId, when fetch fails, then should throw exception`() = runTest {
        // Given
        val movieId = 101
        val exception = CustomExceptionDomainModel.Api(CustomApiExceptionDomainModel.NetworkExceptionDomainModel)
        coEvery { moviesRemoteDataSource.fetchMovieDetails(movieId) } throws exception

        // When
        movieDetailsRepositoryImpl.fetchMovieDetailsFirstSection(movieId)
    }

    /**
     * Test Case 3: Fetch Similar Movies - Success
     */
    @Test
    fun `fetchSimilarMovies, given valid movieId, when fetch is successful, then should return list of MovieDomainModel`() = runTest {
        // Given
        val movieId = 102
        val moviesResponse = fakeMoviesResponseTest
        val expectedDomainModels = moviesResponse.movieListDataModel.map { it.toMovieDomainModel() }
        coEvery { moviesRemoteDataSource.fetchSimilarMovies(movieId) } returns moviesResponse.movieListDataModel

        // When
        val result = movieDetailsRepositoryImpl.fetchSimilarMovies(movieId)

        // Then
        assertEquals(expectedDomainModels, result)
    }

    /**
     * Test Case 4: Fetch Similar Movies - Failure
     */
    @Test(expected = CustomApiExceptionDomainModel::class)
    fun `fetchSimilarMovies, given valid movieId, when fetch fails, then should throw exception`() = runTest {
        // Given
        val movieId = 102
        val exception = CustomApiExceptionDomainModel.NetworkExceptionDomainModel
        coEvery { moviesRemoteDataSource.fetchSimilarMovies(movieId) } throws exception

        // When
        movieDetailsRepositoryImpl.fetchSimilarMovies(movieId)
    }

    /**
     * Test Case 5: Toggle Favorite Status
     */
    @Test
    fun `toggleFavoriteStatus, given a movie, when it is already favorite, then should delete it from favorites`() = runTest {
        // Given
        val movieUiModel = fakeMovieDetailsUiModel
        val favouriteEntity = movieUiModel.toFavouriteMovieEntity()
        coEvery { moviesLocalDataSource.getFavouriteMovie(movieUiModel.id) } returns favouriteEntity
        coEvery { moviesLocalDataSource.deleteFavouriteMovie(favouriteEntity) } returns Unit

        // When
        movieDetailsRepositoryImpl.toggleFavoriteStatus(movieUiModel)

        // Then
        coVerify { moviesLocalDataSource.deleteFavouriteMovie(favouriteEntity) }
    }

    @Test
    fun `toggleFavoriteStatus, given a movie, when it is not favorite, then should insert it to favorites`() = runTest {
        // Given
        val movieUiModel = fakeMovieDetailsUiModel
        val favouriteEntity = movieUiModel.toFavouriteMovieEntity()
        coEvery { moviesLocalDataSource.getFavouriteMovie(movieUiModel.id) } returns null
        coEvery { moviesLocalDataSource.insertFavouriteMovie(favouriteEntity) } returns Unit

        // When
        movieDetailsRepositoryImpl.toggleFavoriteStatus(movieUiModel)

        // Then
        coVerify { moviesLocalDataSource.insertFavouriteMovie(favouriteEntity) }
    }

    /**
     * Test Case 6: Check If Movie is Favorite
     */
    @Test
    fun `isFavorite, given a movieId, when it is a favorite, then should return true`() = runTest {
        // Given
        val movieId = 103
        coEvery { moviesLocalDataSource.getFavouriteMovie(movieId) } returns fakeFavouriteMoviesListTest.first()

        // When
        val result = movieDetailsRepositoryImpl.isFavorite(movieId)

        // Then
        assertTrue(result)
    }

    @Test
    fun `isFavorite, given a movieId, when it is not a favorite, then should return false`() = runTest {
        // Given
        val movieId = 104
        coEvery { moviesLocalDataSource.getFavouriteMovie(movieId) } returns null

        // When
        val result = movieDetailsRepositoryImpl.isFavorite(movieId)

        // Then
        assertFalse(result)
    }
}
