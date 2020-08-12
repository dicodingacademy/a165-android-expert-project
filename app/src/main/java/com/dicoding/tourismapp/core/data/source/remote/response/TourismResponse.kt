package com.dicoding.tourismapp.core.data.source.remote.response

data class TourismResponse(
    val id: String,
    val name: String,
    val description: String,
    val address: String,
    val longitude: Double,
    val latitude: Double,
    val like: Int,
    val image: String
)

