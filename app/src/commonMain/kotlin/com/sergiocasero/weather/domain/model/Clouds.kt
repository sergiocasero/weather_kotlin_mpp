package com.sergiocasero.weather.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Clouds(
    val all: Int
)
