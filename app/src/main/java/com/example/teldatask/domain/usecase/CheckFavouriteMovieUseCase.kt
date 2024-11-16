package com.example.teldatask.domain.usecase

import com.example.teldatask.domain.repository.MovieDetailsRepository
import javax.inject.Inject

class CheckFavouriteMovieUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) {
    suspend operator fun invoke(movieId: Int):Boolean{
        return movieDetailsRepository.isFavorite(movieId)
    }
}