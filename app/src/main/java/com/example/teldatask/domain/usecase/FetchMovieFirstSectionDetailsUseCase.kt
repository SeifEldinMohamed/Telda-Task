package com.example.teldatask.domain.usecase

import com.example.teldatask.domain.model.details.MovieDetailsDomainModel
import com.example.teldatask.domain.repository.MovieDetailsRepository
import javax.inject.Inject

class FetchMovieFirstSectionDetailsUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) {
    suspend operator fun invoke(movieId: Int): MovieDetailsDomainModel {
        return movieDetailsRepository.fetchMovieDetailsFirstSection(movieId)
    }

}