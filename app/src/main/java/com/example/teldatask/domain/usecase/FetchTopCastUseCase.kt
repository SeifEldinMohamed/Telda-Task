package com.example.teldatask.domain.usecase

import com.example.teldatask.data.mapper.toCustomApiExceptionDomainModel
import com.example.teldatask.domain.model.details.CastDomainModel
import com.example.teldatask.domain.model.details.CreditsDomainModel
import com.example.teldatask.domain.model.details.CrewDomainModel
import com.example.teldatask.domain.model.details.Departments
import com.example.teldatask.domain.repository.MovieDetailsRepository
import javax.inject.Inject

class FetchTopCastUseCase @Inject constructor(
    private val movieDetailsRepository: MovieDetailsRepository
) {
    suspend operator fun invoke(movieIds: List<Int>): CreditsDomainModel {
        val actors = mutableListOf<CastDomainModel>()
        val directors = mutableListOf<CrewDomainModel>()

        for (movieId in movieIds) {
            try {
                val credits = movieDetailsRepository.fetchMovieCredits(movieId)

                actors.addAll(credits.cast.filter { it.knownForDepartment == Departments.Acting.toString() })
                directors.addAll(credits.crew.filter { it.knownForDepartment == Departments.Directing.toString() })

            } catch (e: Exception) {
                throw e.toCustomApiExceptionDomainModel()
            }
        }

        val topActors = actors.distinctBy { it.id }.sortedByDescending { it.popularity }.take(5)
        val topDirectors = directors.distinctBy { it.id }.sortedByDescending { it.popularity }.take(5)

        return CreditsDomainModel(id = 0, cast = topActors, crew = topDirectors)
    }
}