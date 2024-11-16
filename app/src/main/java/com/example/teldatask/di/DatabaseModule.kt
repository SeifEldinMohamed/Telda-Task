package com.example.teldatask.di

import android.content.Context
import androidx.room.Room
import com.example.teldatask.data.Constants.Companion.DATABASE_NAME
import com.example.teldatask.data.data_sources.local.room.FavouriteDataBase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        FavouriteDataBase::class.java,
        DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun provideFavouriteDao(favouriteDatabase: FavouriteDataBase) = favouriteDatabase.favouriteDao()

}
