package com.example.teldatask.domain.usecase

import com.example.teldatask.domain.model.MovieDomainModel
import com.example.teldatask.domain.repository.MoviesRepository
import javax.inject.Inject

class FetchPopularMovieListUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(): List<MovieDomainModel> = moviesRepository.fetchPopularMovies()
}