package com.sergiocasero.weather.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Wind(
    val deg: Double,
    val speed: Double
)
