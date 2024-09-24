package com.dicoding.tourismapp.core.domain.usecase

import com.dicoding.tourismapp.core.data.Resource
import com.dicoding.tourismapp.core.domain.model.Tourism
import io.reactivex.rxjava3.core.Flowable


interface TourismUseCase {
    fun getAllTourism(): Flowable<Resource<List<Tourism>>>
    fun getFavoriteTourism(): Flowable<List<Tourism>>
    fun setFavoriteTourism(tourism: Tourism, state: Boolean)
}