package com.example.teldatask.domain.usecase

import com.example.teldatask.domain.model.MovieDomainModel
import com.example.teldatask.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchPopularMovieListUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    operator fun invoke(): Flow<List<MovieDomainModel>> = moviesRepository.fetchPopularMovies()
}