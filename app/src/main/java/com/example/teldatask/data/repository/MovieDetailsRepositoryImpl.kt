package com.example.teldatask.data.repository

import com.example.teldatask.data.data_sources.local.MoviesLocalDataSource
import com.example.teldatask.data.data_sources.remote.MoviesRemoteDataSource
import com.example.teldatask.data.mapper.toCreditsDomainModel
import com.example.teldatask.data.mapper.toFavouriteMovieEntity
import com.example.teldatask.data.mapper.toMovieDetailsDomainModel
import com.example.teldatask.data.mapper.toMovieDomainModel
import com.example.teldatask.domain.model.details.MovieDetailsDomainModel
import com.example.teldatask.domain.model.MovieDomainModel
import com.example.teldatask.domain.model.details.CreditsDomainModel
import com.example.teldatask.domain.repository.MovieDetailsRepository
import com.example.teldatask.presentation.screens.movie_details_screen.model.MovieDetailsUiModel
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val moviesRemoteDataSource: MoviesRemoteDataSource,
    private val moviesLocalDataSource: MoviesLocalDataSource
) : MovieDetailsRepository {
    override suspend fun fetchMovieDetailsFirstSection(movieId: Int): MovieDetailsDomainModel {
        return try {
            moviesRemoteDataSource.fetchMovieDetails(movieId = movieId).toMovieDetailsDomainModel()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun fetchSimilarMovies(movieId: Int): List<MovieDomainModel> {
        return try {
            moviesRemoteDataSource.fetchSimilarMovies(movieId = movieId).map { it.toMovieDomainModel() }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun fetchMovieCredits(movieId: Int): CreditsDomainModel {
        return try {
            moviesRemoteDataSource.fetchMovieCredits(movieId = movieId).toCreditsDomainModel()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun toggleFavoriteStatus(movie: MovieDetailsUiModel) {
        val favouriteMovieEntity = movie.toFavouriteMovieEntity()

        // Check if it's already a favorite
        val isAlreadyFavorite = moviesLocalDataSource.getFavouriteMovie(movie.id) != null
        if (isAlreadyFavorite) {
            moviesLocalDataSource.deleteFavouriteMovie(favouriteMovieEntity)
        } else {
            moviesLocalDataSource.insertFavouriteMovie(favouriteMovieEntity)
        }
    }

    override suspend fun isFavorite(movieId: Int): Boolean {
        return moviesLocalDataSource.getFavouriteMovie(movieId) != null
    }
}