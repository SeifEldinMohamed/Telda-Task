package com.example.teldatask.domain.usecase

import com.example.teldatask.domain.model.MovieDomainModel
import com.example.teldatask.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {
     operator fun invoke(query: String): Flow<List<MovieDomainModel>> {
        return moviesRepository.searchMovies(query = query)
    }
}