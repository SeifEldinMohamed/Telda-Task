package com.example.teldatask.domain.fake_data

import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movie_details.Genre
import com.example.teldatask.domain.model.MovieDomainModel
import com.example.teldatask.domain.model.details.CastDomainModel
import com.example.teldatask.domain.model.details.CreditsDomainModel
import com.example.teldatask.domain.model.details.CrewDomainModel
import com.example.teldatask.domain.model.details.MovieDetailsDomainModel
import com.example.teldatask.presentation.screens.movie_details_screen.model.CastUiModel
import com.example.teldatask.presentation.screens.movie_details_screen.model.CreditsUiModel
import com.example.teldatask.presentation.screens.movie_details_screen.model.CrewUiModel
import com.example.teldatask.presentation.screens.movie_details_screen.model.MovieDetailsUiModel
import com.example.teldatask.presentation.screens.movies_home_screen.model.MovieUiModel

val fakeMovieDetailsDomainModelTest = MovieDetailsDomainModel(
    id = 1,
    posterPath = "https://image.tmdb.org/t/p/w500/6MKr3KgOLmzOP6MSuZERO41Lpkt.jpg",
    categories = listOf(Genre(id = 1, name = "Action"), Genre(id = 2, name = "Horror")),
    name = "The Tomorrow War",
    rating = 7.5,
    releaseDate = "2021-07-02",
    revenue = "120000",
    status = "Released",
    details = "The world is stunned when a group of time travelers arrive from the year 2051 to deliver an urgent message: Thirty years in the future, mankind is losing a global war against a deadly alien species. The only hope for survival is for soldiers and civilians from the present to be transported to the future and join the fight. Among those recruited is high school",
    isFavourite = true
)

val fakeSimilarMoviesListTest = listOf(
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
    MovieDomainModel(
        id = 3,
        title = "The Godfather",
        posterPath = "https://image.tmdb.org/t/p/w500/1GVnPQcMd5V4s6g553089N5Qp.jpg",
        releaseDate = "1972-03-24",
        voteAverage = 8.5,
        overview = "The Corleone family is one of the most powerful crime families in New York City.",
        originalLanguage = "en",
        isFavourite = false
    ),
    MovieDomainModel(
        id = 4,
        title = "The Godfather",
        posterPath = "https://image.tmdb.org/t/p/w500/1GVnPQcMd5V4s6g553089N5Qp.jpg",
        releaseDate = "1972-03-24",
        voteAverage = 8.5,
        overview = "The Corleone family is one of the most powerful crime families in New York City.",
        originalLanguage = "en",
        isFavourite = false
    ),
    MovieDomainModel(
        id = 5,
        title = "The Godfather",
        posterPath = "https://image.tmdb.org/t/p/w500/1GVnPQcMd5V4s6g553089N5Qp.jpg",
        releaseDate = "1972-03-24",
        voteAverage = 8.5,
        overview = "The Corleone family is one of the most powerful crime families in New York City.",
        originalLanguage = "en",
        isFavourite = false
    ),
)

val fakeCreditsListDomainModelTest = CreditsDomainModel(
    id = 0,
    cast = listOf(
        CastDomainModel(
            id = 4,
            name = "Tom Holland",
            popularity = 80.1,
            profilePath = "https://image.tmdb.org/t/p/w500/2qhIDp44cAqP2clOgt2afQI07X8.jpg",
            knownForDepartment = "Acting"
        ),
        CastDomainModel(
            id = 2,
            name = "Scarlett Johansson",
            popularity = 90.7,
            profilePath = "https://image.tmdb.org/t/p/w500/6NsMbJXRlDZuDzatN2akFdGuTvx.jpg",
            knownForDepartment = "Acting"
        ),
        CastDomainModel(
            id = 1,
            name = "Robert Downey Jr.",
            popularity = 98.5,
            profilePath = "https://image.tmdb.org/t/p/w500/1YjdSym1jTG7xjHSI0yGGWEsw5i.jpg",
            knownForDepartment = "Acting"
        ),
        CastDomainModel(
            id = 3,
            name = "Chris Hemsworth",
            popularity = 85.3,
            profilePath = "https://image.tmdb.org/t/p/w500/jpurJ9jAcLCYjgHHfYF32m3zJYm.jpg",
            knownForDepartment = "Acting"
        ),
        CastDomainModel(
            id = 5,
            name = "Chris Evans",
            popularity = 78.4,
            profilePath = "https://image.tmdb.org/t/p/w500/3bOGNsHlrswhyW79uvIHH1V43JI.jpg",
            knownForDepartment = "Acting"
        ),
        CastDomainModel(
            id = 6,
            name = "Jason Momoa",
            popularity = 71.2,
            profilePath = "https://image.tmdb.org/t/p/w500/PSK6GmsVwdhqz9cd1lwzC6a7EA.jpg",
            knownForDepartment = "Acting"
        )
    ),
    crew = listOf(
        CrewDomainModel(
            id = 4,
            name = "Martin Scorsese",
            popularity = 87.4,
            profilePath = "https://image.tmdb.org/t/p/w500/6FkqwqLEcDZOEAnBBfKAniwNxtx.jpg",
            knownForDepartment = "Directing"
        ),
        CrewDomainModel(
            id = 2,
            name = "Steven Spielberg",
            popularity = 92.5,
            profilePath = "https://image.tmdb.org/t/p/w500/kU3B75TyRiCgE270EyZnHjfivoq.jpg",
            knownForDepartment = "Directing"
        ),
        CrewDomainModel(
            id = 1,
            name = "Christopher Nolan",
            popularity = 95.0,
            profilePath = "https://image.tmdb.org/t/p/w500/jMlM4u9pSbz6C6ZlJwhg6Ob9XZV.jpg",
            knownForDepartment = "Directing"
        ),
        CrewDomainModel(
            id = 3,
            name = "Quentin Tarantino",
            popularity = 89.8,
            profilePath = "https://image.tmdb.org/t/p/w500/9FCv7DP7pOlLBvhvvNPgzbzOWbt.jpg",
            knownForDepartment = "Directing"
        ),
        CrewDomainModel(
            id = 5,
            name = "James Cameron",
            popularity = 85.9,
            profilePath = "https://image.tmdb.org/t/p/w500/9NAZnTjBQ9WULe7eFF5nQ5vh7yP.jpg",
            knownForDepartment = "Directing"
        ),
        CrewDomainModel(
            id = 6,
            name = "Ridley Scott",
            popularity = 84.3,
            profilePath = "https://image.tmdb.org/t/p/w500/oTnlCUv6jYfd0Ntm3XpT7QmSBCc.jpg",
            knownForDepartment = "Directing"
        )
    )
)