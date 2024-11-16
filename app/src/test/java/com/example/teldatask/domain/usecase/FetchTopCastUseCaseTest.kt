package com.example.teldatask.domain.usecase

import com.example.teldatask.domain.fake_data.fakeCreditsListDomainModelTest
import com.example.teldatask.domain.model.CustomApiExceptionDomainModel
import com.example.teldatask.domain.model.CustomExceptionDomainModel
import com.example.teldatask.domain.repository.MovieDetailsRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class FetchTopCastUseCaseTest {

    private lateinit var movieDetailsRepository: MovieDetailsRepository
    private lateinit var fetchTopCastUseCase: FetchTopCastUseCase

    @Before
    fun setup() {
        movieDetailsRepository = mockk() // Mock the MovieDetailsRepository
        fetchTopCastUseCase = FetchTopCastUseCase(movieDetailsRepository)
    }

    /**
     * Test Case: Success scenario
     * Given: A list of movieIds, when repository returns credits,
     * Then: The top actors and directors should be returned correctly.
     */
    @Test
    fun `given movieIds, when fetchTopCastUseCase is called, then it should return top actors and directors`() = runTest {
        // Given
        val movieIds = listOf(1, 2)
        val credits = fakeCreditsListDomainModelTest
        val top1Actor = "Robert Downey Jr."
        val top1ADirector = "Christopher Nolan"
        coEvery { movieDetailsRepository.fetchMovieCredits(any()) } returns credits

        // When
        val result = fetchTopCastUseCase(movieIds)

        // Then
        // should return top 5 actors
        assertEquals(5, result.cast.size)
        assertEquals(5, result.crew.size)

        // Check if the top actors and directors are sorted correctly by popularity
        assertEquals(top1Actor, result.cast.first().name)
        assertEquals(top1ADirector, result.crew.first().name)
    }

    /**
     * Test Case: Failure scenario - repository throws exception
     * Given: A list of movieIds, when the repository throws an exception,
     * Then: The use case should propagate the exception.
     */
    @Test
    fun `given movieIds, when repository throws exception, then it should propagate the exception`() = runTest {
        // Given
        val movieIds = listOf(1, 2)
        coEvery { movieDetailsRepository.fetchMovieCredits(any()) } throws CustomExceptionDomainModel.Api(CustomApiExceptionDomainModel.NetworkExceptionDomainModel)

        // When & Then
        assertThrows(CustomExceptionDomainModel.Api::class.java) {
            runBlocking { fetchTopCastUseCase(movieIds) }
        }
    }
}
