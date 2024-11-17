package com.example.teldatask

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import com.example.teldatask.presentation.model.CustomApiExceptionUiModel
import com.example.teldatask.presentation.screens.movies_home_screen.MoviesHomeContent
import org.junit.Rule
import org.junit.Test
import com.example.teldatask.presentation.screens.movies_home_screen.MoviesHomeUiState
import com.example.teldatask.presentation.screens.movies_home_screen.preview_data.fakePopularMovieListUiModel
import org.junit.Before

class MoviesHomeContentUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var moviesHomeUiState: MoviesHomeUiState
    private var searchQuery by mutableStateOf("")
    private lateinit var context:Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext<Context>()
        searchQuery = ""
        moviesHomeUiState = MoviesHomeUiState.PopularMoviesList(
            popularMoviesList = fakePopularMovieListUiModel
        )
    }

    @Test
    fun testEmptyStateDisplayed() {
        // Arrange
        val emptyState = MoviesHomeUiState.EmptyState

        // Act
        composeTestRule.setContent {
            MoviesHomeContent(
                moviesHomeUiState = emptyState,
                searchQuery = searchQuery,
                onMovieClick = {},
                onQueryChanged = {},
                onRefreshButtonClicked = {}
            )
        }

        // Assert  that the empty section is displayed
        composeTestRule.onNodeWithTag(context.getString(R.string.test_tag_empty_section)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.no_results_found)).assertIsDisplayed()
    }

    @Test
    fun testLoadingStateDisplayed() {
        // Arrange
        val loadingState = MoviesHomeUiState.Loading(isLoading = true)

        // Act
        composeTestRule.setContent {
            MoviesHomeContent(
                moviesHomeUiState = loadingState,
                searchQuery = searchQuery,
                onMovieClick = {},
                onQueryChanged = {},
                onRefreshButtonClicked = {}
            )
        }

        // Assert that shimmer effect is displayed
        composeTestRule.onNodeWithTag(context.getString(R.string.test_tag_shimmer_movies_list)).assertIsDisplayed()
    }

    @Test
    fun testMovieListDisplayed() {
        // Arrange
        val popularMoviesState = MoviesHomeUiState.PopularMoviesList(
            popularMoviesList = fakePopularMovieListUiModel
        )

        // Act
        composeTestRule.setContent {
            MoviesHomeContent(
                moviesHomeUiState = popularMoviesState,
                searchQuery = searchQuery,
                onMovieClick = { id -> },
                onQueryChanged = {},
                onRefreshButtonClicked = {}
            )
        }

        // Assert that the movie items are displayed
        composeTestRule.onNodeWithTag(context.getString(R.string.test_tag_movie_item, fakePopularMovieListUiModel.first().id)).assertIsDisplayed()
        composeTestRule.onNodeWithTag(context.getString(R.string.test_tag_movie_item, fakePopularMovieListUiModel.last().id)).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakePopularMovieListUiModel.first().title).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakePopularMovieListUiModel.last().title).assertIsDisplayed()
    }

    @Test
    fun testErrorStateDisplayed() {
        // Arrange
        val errorState = MoviesHomeUiState.ApiError(
            customApiErrorExceptionUiModel = CustomApiExceptionUiModel.Network
        )

        // Act
        composeTestRule.setContent {
            MoviesHomeContent(
                moviesHomeUiState = errorState,
                searchQuery = searchQuery,
                onMovieClick = {},
                onQueryChanged = {},
                onRefreshButtonClicked = {}
            )
        }

        // Assert: Verify that the error message and retry button are displayed
        composeTestRule.onNodeWithTag(context.getString(R.string.test_tag_error_section)).assertIsDisplayed()
        composeTestRule.onNodeWithText(context.getString(R.string.retry)).assertIsDisplayed()
    }

    @Test
    fun testSearchBarFunctionality() {
        // Arrange
        searchQuery = "Deadpool"
        // Act: Set the composable
        composeTestRule.setContent {
            MoviesHomeContent(
                moviesHomeUiState = moviesHomeUiState,
                searchQuery = searchQuery,
                onMovieClick = {},
                onQueryChanged = { query -> searchQuery = query },
                onRefreshButtonClicked = {}
            )
        }

        // Act
        composeTestRule.onNodeWithTag(context.getString(R.string.test_tag_searchbar_textfield)).performTextInput(searchQuery)

        // Assert that the search query is updated
        composeTestRule.onNodeWithTag(context.getString(R.string.test_tag_searchbar_textfield)).assert(hasText(searchQuery))
    }

    @Test
    fun testSearchBarDisplaysHintText() {
        // Act
        composeTestRule.setContent {
            MoviesHomeContent(
                moviesHomeUiState = moviesHomeUiState,
                searchQuery = "",
                onMovieClick = {},
                onQueryChanged = {},
                onRefreshButtonClicked = {}
            )
        }

        // Assert that the search bar shows hint text initially
        composeTestRule.onNodeWithTag(context.getString(R.string.test_tag_searchbar_textfield))
            .assert(hasText(context.getString(R.string.search_for_a_movie_hint)))
    }

    @Test
    fun testMovieClickTriggersCallback() {
        // Arrange
        var clickedMovieId: Int? = null
        val popularMoviesState = MoviesHomeUiState.PopularMoviesList(
            popularMoviesList = fakePopularMovieListUiModel
        )

        // Act
        composeTestRule.setContent {
            MoviesHomeContent(
                moviesHomeUiState = popularMoviesState,
                searchQuery = searchQuery,
                onMovieClick = { id -> clickedMovieId = id },
                onQueryChanged = {},
                onRefreshButtonClicked = {}
            )
        }

        // Act: Perform click on first movie item
        composeTestRule.onNodeWithTag(context.getString(R.string.test_tag_movie_item, fakePopularMovieListUiModel.first().id))
            .performClick()

        // Assert that click callback is triggered with correct movie ID
        assert(clickedMovieId == fakePopularMovieListUiModel.first().id)
    }

    @Test
    fun testRetryButtonTriggersCallback() {
        // Arrange
        var retryClicked = false
        val errorState = MoviesHomeUiState.ApiError(
            customApiErrorExceptionUiModel = CustomApiExceptionUiModel.Network
        )

        // Act
        composeTestRule.setContent {
            MoviesHomeContent(
                moviesHomeUiState = errorState,
                searchQuery = searchQuery,
                onMovieClick = {},
                onQueryChanged = {},
                onRefreshButtonClicked = { retryClicked = true }
            )
        }

        composeTestRule.onNodeWithText(context.getString(R.string.retry)).performClick()

        // Assert that the retry button click callback is triggered
        assert(retryClicked)
    }

    @Test
    fun testFavouriteIconVisibilityForMovies() {
        // Arrange
        val popularMoviesState = MoviesHomeUiState.PopularMoviesList(
            popularMoviesList = fakePopularMovieListUiModel
        )

        // Act
        composeTestRule.setContent {
            MoviesHomeContent(
                moviesHomeUiState = popularMoviesState,
                searchQuery = searchQuery,
                onMovieClick = {},
                onQueryChanged = {},
                onRefreshButtonClicked = {}
            )
        }

        // Assert: Check if favourite icon is displayed for favourite movies
        fakePopularMovieListUiModel.forEach { movie ->
            if (movie.isFavourite) {
                composeTestRule.onNodeWithTag(context.getString(R.string.test_tag_favourite_icon, movie.id), useUnmergedTree = true)
                    .assertIsDisplayed()
            }
        }
    }

    @Test
    fun testNonFavouriteIconNotDisplayedForNonFavouriteMovies() {
        // Arrange
        val popularMoviesState = MoviesHomeUiState.PopularMoviesList(
            popularMoviesList = fakePopularMovieListUiModel
        )

        // Act
        composeTestRule.setContent {
            MoviesHomeContent(
                moviesHomeUiState = popularMoviesState,
                searchQuery = searchQuery,
                onMovieClick = {},
                onQueryChanged = {},
                onRefreshButtonClicked = {}
            )
        }

        // Assert that non-favourite movies do not show the favourite icon
        fakePopularMovieListUiModel.forEach { movie ->
            if (!movie.isFavourite) {
                composeTestRule.onNodeWithTag(context.getString(R.string.test_tag_favourite_icon, movie.id)).assertDoesNotExist()
            }
        }
    }

}
