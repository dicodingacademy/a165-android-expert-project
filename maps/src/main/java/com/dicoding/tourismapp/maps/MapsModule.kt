package com.dicoding.tourismapp.maps

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mapsModule = module {
    viewModel { MapsViewModel(get()) }
}