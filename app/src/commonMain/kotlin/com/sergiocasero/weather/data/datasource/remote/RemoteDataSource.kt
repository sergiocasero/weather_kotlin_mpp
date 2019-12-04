package com.sergiocasero.weather.data.datasource.remote

import com.sergiocasero.weather.domain.Either
import com.sergiocasero.weather.domain.Error
import com.sergiocasero.weather.domain.model.Forecast

interface RemoteDataSource {
    suspend fun getLatLngForecast(latitude: Double, longitude: Double): Either<Error, List<Forecast>>
}
