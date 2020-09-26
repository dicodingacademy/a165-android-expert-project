package com.dicoding.tourismapp.home

import androidx.lifecycle.ViewModel
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase

class HomeViewModel(tourismUseCase: TourismUseCase) : ViewModel() {

    val tourism = tourismUseCase.getAllTourism()

}

