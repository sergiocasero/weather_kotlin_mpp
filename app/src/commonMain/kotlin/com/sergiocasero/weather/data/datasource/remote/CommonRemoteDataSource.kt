package com.sergiocasero.weather.data.datasource.remote

import com.sergiocasero.weather.domain.Either
import com.sergiocasero.weather.domain.Error
import com.sergiocasero.weather.domain.model.Forecast
import com.sergiocasero.weather.domain.model.WeatherResponse
import com.sergiocasero.weather.extensions.apiUrl
import com.sergiocasero.weather.extensions.execute
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import kotlinx.serialization.json.Json

class CommonRemoteDataSource : RemoteDataSource {

    private val client = HttpClient {
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json.nonstrict)
        }
    }

    override suspend fun getLatLngForecast(latitude: Double, longitude: Double): Either<Error, List<Forecast>> =
        execute {
            client.get<WeatherResponse> { apiUrl("/data/2.5/forecast?lat=$latitude&lon=$longitude") }.list
        }
}
