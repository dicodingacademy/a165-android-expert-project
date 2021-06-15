package com.dicoding.tourismapp.di

import com.dicoding.tourismapp.core.domain.usecase.TourismInteractor
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideTourismUseCase(tourismInteractor: TourismInteractor): TourismUseCase

}
