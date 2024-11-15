package com.example.teldatask.domain.usecase

import com.example.teldatask.domain.model.MovieDomainModel
import com.example.teldatask.domain.repository.MovieDetailsRepository
import com.example.teldatask.domain.repository.MoviesRepository
import javax.inject.Inject

class FetchSimilarMoviesListUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) {
    suspend operator fun invoke(movieId:Int): List<MovieDomainModel> {
        return movieDetailsRepository.fetchSimilarMovies(movieId).take(5)
    }
}