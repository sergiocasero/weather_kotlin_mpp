package com.sergiocasero.weather.data.repository

import com.sergiocasero.weather.data.datasource.local.LocalDataSource
import com.sergiocasero.weather.data.datasource.remote.RemoteDataSource
import com.sergiocasero.weather.domain.Either
import com.sergiocasero.weather.domain.Error
import com.sergiocasero.weather.domain.model.Forecast

class CommonRepository(private val local: LocalDataSource, private val remote: RemoteDataSource) : Repository {

    override suspend fun getLatLngForecast(latitude: Double, longitude: Double): Either<Error, List<Forecast>> =
        remote.getLatLngForecast(latitude, longitude)

}
