package com.example.teldatask.domain.usecase

import com.example.teldatask.domain.repository.MovieDetailsRepository
import com.example.teldatask.presentation.screens.movie_details_screen.model.MovieDetailsUiModel
import javax.inject.Inject

class ToggleFavouriteStatusUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) {
    suspend operator fun invoke(movie: MovieDetailsUiModel){
        return movieDetailsRepository.toggleFavoriteStatus(movie)
    }
}