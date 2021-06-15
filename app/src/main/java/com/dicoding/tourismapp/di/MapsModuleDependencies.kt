package com.dicoding.tourismapp.di

import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface MapsModuleDependencies {

    fun tourismUseCase(): TourismUseCase
}