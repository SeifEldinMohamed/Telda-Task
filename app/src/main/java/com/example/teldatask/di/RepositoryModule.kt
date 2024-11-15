package com.example.teldatask.di

import com.example.teldatask.data.data_sources.remote.MoviesRemoteDataSource
import com.example.teldatask.data.repository.MovieDetailsRepositoryImpl
import com.example.teldatask.data.repository.MoviesRepositoryImpl
import com.example.teldatask.domain.repository.MovieDetailsRepository
import com.example.teldatask.domain.repository.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideMoviesRepositoryImp(
        moviesRemoteDataSource: MoviesRemoteDataSource
    ): MoviesRepository {
        return MoviesRepositoryImpl(moviesRemoteDataSource)
    }

    @Provides
    @Singleton
    fun provideMovieDetailsRepositoryImp(
        moviesRemoteDataSource: MoviesRemoteDataSource
    ): MovieDetailsRepository {
        return MovieDetailsRepositoryImpl(moviesRemoteDataSource)
    }
}