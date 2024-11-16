package com.example.teldatask.presentation.mapper

import com.example.teldatask.domain.model.MovieDomainModel
import com.example.teldatask.domain.fake_data.fakePopularMovieListDomainModelTest
import com.example.teldatask.presentation.fake_data.fakePopularMovieListUiModelTest
import com.example.teldatask.presentation.screens.movies_home_screen.model.MovieUiModel
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(value = Parameterized::class)
class ToMovieUiModelMapperTest(
    private val inputData: MovieDomainModel,
    private val expectedOutput: MovieUiModel
) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: InputModel = {0}, Expected = {1}")
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf(fakePopularMovieListDomainModelTest[0], fakePopularMovieListUiModelTest[0]),
                arrayOf(fakePopularMovieListDomainModelTest[1], fakePopularMovieListUiModelTest[1])
            )
        }
    }

    /**
     * Test Case: toMovieUIModel Mapping
     * Given: A MovieDomainModel input data.
     * When: Converting the input to MovieUiModel using `toMovieUIModel()`.
     * Then: It should correctly map and return the expected MovieUiModel.
     */
    @Test
    fun `toMovieUIModel, given MovieDomainModel input, when mapping to ui model, then should return expected MovieUiModel`() {
        // When
        val result = inputData.toMovieUIModel()

        // Then
        assertEquals(expectedOutput, result)
    }
}