package com.example.teldatask.data.mapper

import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movies_list.MovieDataModel
import com.example.teldatask.domain.model.MovieDomainModel
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class MovieDataModelToDomainModelMapperTest(
    private val inputData: MovieDataModel,
    private val expectedOutput: MovieDomainModel
) {

    companion object {
        private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500/"

        @JvmStatic
        @Parameterized.Parameters(name = "{index}: InputModel = {0}, Expected = {1}")
        fun data(): Collection<Array<Any>> {
            return listOf(
                arrayOf(
                    MovieDataModel(
                        adult = false,
                        backdropPath = "/fakeBackdropPath1.jpg",
                        genreIds = listOf(1, 2, 3),
                        id = 101,
                        originalLanguage = "en",
                        originalTitle = "Original Title 1",
                        overview = "This is a fake overview for movie 1.",
                        popularity = 8.5,
                        posterPath = "fakePosterPath1.jpg",
                        releaseDate = "2022-01-01",
                        title = "Fake Movie 1",
                        video = false,
                        voteAverage = 7.5,
                        voteCount = 100
                    ),
                    MovieDomainModel(
                        id = 101,
                        title = "Fake Movie 1",
                        posterPath = IMAGE_BASE_URL + "fakePosterPath1.jpg",
                        releaseDate = "2022-01-01",
                        voteAverage = 7.5,
                        overview = "This is a fake overview for movie 1.",
                        originalLanguage = "en",
                        isFavourite = false
                    )
                ),
                arrayOf(
                    MovieDataModel(
                        adult = true,
                        backdropPath = "/fakeBackdropPath2.jpg",
                        genreIds = listOf(4, 5, 6),
                        id = 102,
                        originalLanguage = "fr",
                        originalTitle = "Original Title 2",
                        overview = "This is a fake overview for movie 2.",
                        popularity = 9.0,
                        posterPath = "fakePosterPath2.jpg",
                        releaseDate = "2022-02-02",
                        title = "Fake Movie 2",
                        video = false,
                        voteAverage = 8.0,
                        voteCount = 200
                    ),
                    MovieDomainModel(
                        id = 102,
                        title = "Fake Movie 2",
                        posterPath = IMAGE_BASE_URL + "fakePosterPath2.jpg",
                        releaseDate = "2022-02-02",
                        voteAverage = 8.0,
                        overview = "This is a fake overview for movie 2.",
                        originalLanguage = "fr",
                        isFavourite = false
                    )
                )
            )
        }
    }

    /**
     * Test Case: toMovieDomainModel Mapping
     * Given: A MovieDataModel input data.
     * When: Converting the input to MovieDomainModel using `toMovieDomainModel()`.
     * Then: It should correctly map and return the expected MovieDomainModel.
     */
    @Test
    fun `toMovieDomainModel, given MovieDataModel input, when mapping to domain model, then should return expected MovieDomainModel`() {
        // When
        val result = inputData.toMovieDomainModel()

        // Then
        assertEquals(expectedOutput, result)
    }
}
