package com.sergiocasero.weather.data.repository

import com.sergiocasero.weather.domain.Either
import com.sergiocasero.weather.domain.Error
import com.sergiocasero.weather.domain.model.Forecast

interface Repository {
    suspend fun getLatLngForecast(latitude: Double, longitude: Double): Either<Error, List<Forecast>>
}
