package com.example.teldatask.domain.fake_data

import com.example.teldatask.domain.model.MovieDomainModel

val fakePopularMovieListDomainModelTest = listOf(
    MovieDomainModel(
        id = 1,
        title = "The Shawshank Redemption",
        posterPath = "https://image.tmdb.org/t/p/w500/q6y0Go1POy89lB7qAUcKc59FH.jpg",
        releaseDate = "1994-09-23",
        voteAverage = 8.6,
        overview = "Two imprisoned men forge a remarkable bond over the years, forming a system of mutual support and survival.",
        originalLanguage = "en",
        isFavourite = true
    ),
    MovieDomainModel(
        id = 2,
        title = "The Godfather",
        posterPath = "https://image.tmdb.org/t/p/w500/1GVnPQcMd5V4s6g553089N5Qp.jpg",
        releaseDate = "1972-03-24",
        voteAverage = 8.5,
        overview = "The Corleone family is one of the most powerful crime families in New York City.",
        originalLanguage = "en",
        isFavourite = false
    ),
)

val fakeSearchedMovieListDomainModelTest = listOf(
    MovieDomainModel(
        id = 1,
        title = "Deadpool",
        posterPath = "https://image.tmdb.org/t/p/w500/inVq3FRqcYIRl2la8iZikYYxFNR.jpg",
        releaseDate = "2016-02-12",
        voteAverage = 8.0,
        overview = "Wade Wilson, a former Special Forces operative turned mercenary, is subjected to a rogue experiment that leaves him with accelerated healing powers, adopting the alter ego Deadpool.",
        originalLanguage = "en",
        isFavourite = true
    ),
    MovieDomainModel(
        id = 2,
        title = "Deadpool 2",
        posterPath = "https://image.tmdb.org/t/p/w500/to0spRl1CMDvyUbOnbb4fTk3VAd.jpg",
        releaseDate = "2018-05-18",
        voteAverage = 7.7,
        overview = "Deadpool forms the X-Force to protect a young mutant from the time-traveling soldier Cable.",
        originalLanguage = "en",
        isFavourite = false
    )
)