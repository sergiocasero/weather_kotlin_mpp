package com.sergiocasero.weather.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Forecast>,
    val message: Int
)
