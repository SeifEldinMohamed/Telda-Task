package com.example.teldatask.data.fake_data

import com.example.teldatask.data.data_sources.local.room.entities.FavouriteMovieEntity
import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movie_details.BelongsToCollection
import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movie_details.Genre
import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movie_details.MovieDetailsResponse
import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movie_details.ProductionCompany
import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movie_details.ProductionCountry
import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movie_details.SpokenLanguage
import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movies_list.MovieDataModel
import com.example.teldatask.data.data_sources.remote.retrofit.datamodel.movies_list.MoviesResponse

val fakeMoviesResponseTest = MoviesResponse(
    page = 1,
    movieListDataModel = listOf(
        MovieDataModel(
            adult = false,
            backdropPath = "/path/to/backdrop1.jpg",
            genreIds = listOf(28, 12, 16),
            id = 101,
            originalLanguage = "en",
            originalTitle = "Fake Movie Title 1",
            overview = "This is a fake overview for the first movie.",
            popularity = 8.5,
            posterPath = "/path/to/poster1.jpg",
            releaseDate = "2024-01-01",
            title = "Fake Movie 1",
            video = false,
            voteAverage = 7.5,
            voteCount = 1000
        ),
        MovieDataModel(
            adult = false,
            backdropPath = "/path/to/backdrop2.jpg",
            genreIds = listOf(35, 18),
            id = 102,
            originalLanguage = "en",
            originalTitle = "Fake Movie Title 2",
            overview = "This is a fake overview for the second movie.",
            popularity = 9.0,
            posterPath = "/path/to/poster2.jpg",
            releaseDate = "2024-06-01",
            title = "Fake Movie 2",
            video = false,
            voteAverage = 8.0,
            voteCount = 2000
        ),
        MovieDataModel(
            adult = false,
            backdropPath = "/path/to/backdrop3.jpg",
            genreIds = listOf(35, 18),
            id = 103,
            originalLanguage = "en",
            originalTitle = "Fake Movie Title 3",
            overview = "This is a fake overview for the second movie.",
            popularity = 9.2,
            posterPath = "/path/to/poster2.jpg",
            releaseDate = "2024-06-01",
            title = "Fake Movie 2",
            video = false,
            voteAverage = 8.7,
            voteCount = 2000
        )
    ),
    totalPages = 1,
    totalResults = 3
)

val fakeFavouriteMoviesListTest = listOf(
    FavouriteMovieEntity(
        id = 101,
        title = "Fake Movie 1",
        overview = "This is a fake overview for the first movie.",
        isFavourite = true
    ),
    FavouriteMovieEntity(
        id = 102,
        title = "Fake Movie 2",
        overview = "This is a fake overview for the second movie.",
        isFavourite = false
    )
)

val fakeMovieDetailsResponseTest = MovieDetailsResponse(
    adult = false,
    backdropPath = "/fake_backdrop_path.jpg",
    belongsToCollection = BelongsToCollection(
        id = 1000,
        name = "Fake Collection",
        posterPath = "/fake_collection_poster.jpg",
        backdropPath = "/fake_collection_backdrop.jpg"
    ),
    budget = 150_000_000,
    genres = listOf(
        Genre(id = 1, name = "Action"),
        Genre(id = 2, name = "Adventure")
    ),
    homepage = "https://www.fakemoviehomepage.com",
    id = 12345,
    imdbId = "tt1234567",
    originCountry = listOf("US"),
    originalLanguage = "en",
    originalTitle = "Fake Movie Original Title",
    overview = "This is a fake overview for the movie, describing the main plot and characters.",
    popularity = 120.5,
    posterPath = "/fake_poster_path.jpg",
    productionCompanies = listOf(
        ProductionCompany(id = 1, name = "Fake Production Co.", logoPath = "/fake_logo.png", originCountry = "US"),
        ProductionCompany(id = 2, name = "Another Fake Productions", logoPath = "/fake_logo.png", originCountry = "UK")
    ),
    productionCountries = listOf(
        ProductionCountry(iso_3166_1 = "US", name = "United States"),
        ProductionCountry(iso_3166_1 = "UK", name = "United Kingdom")
    ),
    releaseDate = "2023-11-16",
    revenue = 500_000_000,
    runtime = 130,
    spokenLanguages = listOf(
        SpokenLanguage(english_name = "English", iso_639_1 = "en", name = "English"),
        SpokenLanguage(english_name = "Spanish", iso_639_1 = "es", name = "Español")
    ),
    status = "Released",
    tagline = "This is a fake tagline for the movie.",
    title = "Fake Movie Title",
    video = false,
    voteAverage = 8.5,
    voteCount = 1500
)
