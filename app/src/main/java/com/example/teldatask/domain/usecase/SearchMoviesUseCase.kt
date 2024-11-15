package com.example.teldatask.domain.usecase

import com.example.teldatask.domain.model.MovieDomainModel
import com.example.teldatask.domain.repository.MoviesRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
    suspend operator fun invoke(query: String): List<MovieDomainModel> {
        return moviesRepository.searchMovies(query = query)
    }
}