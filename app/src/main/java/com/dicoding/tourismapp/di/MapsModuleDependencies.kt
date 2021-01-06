package com.dicoding.tourismapp.di

import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@EntryPoint
@InstallIn(ApplicationComponent::class)
interface MapsModuleDependencies {

    fun tourismUseCase(): TourismUseCase
}