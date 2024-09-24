package com.dicoding.tourismapp.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase

class FavoriteViewModel(tourismUseCase: TourismUseCase) : ViewModel() {
    val favoriteTourism = tourismUseCase.getFavoriteTourism().toLiveData()
}

