package com.sergiocasero.weather.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Forecast(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val rain: Rain? = null,
    val sys: Sys,
    val weather: List<Weather>,
    val wind: Wind
)
