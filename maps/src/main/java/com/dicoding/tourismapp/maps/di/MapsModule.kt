package com.dicoding.tourismapp.maps.di

import com.dicoding.tourismapp.maps.MapsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mapsModule = module {
    viewModel { MapsViewModel(get()) }
}