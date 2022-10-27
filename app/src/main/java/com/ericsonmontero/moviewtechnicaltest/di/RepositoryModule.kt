package com.ericsonmontero.moviewtechnicaltest.di

import com.ericsonmontero.moviewtechnicaltest.data.repository.MovieRepositoryImpl
import com.ericsonmontero.moviewtechnicaltest.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    fun provideMovieRepository(repositoryImpl: MovieRepositoryImpl) : MovieRepository
}