package com.sergiocasero.weather.extensions

import com.sergiocasero.weather.domain.Constants
import com.sergiocasero.weather.domain.Either
import com.sergiocasero.weather.domain.Error
import io.ktor.client.features.ClientRequestException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.http.HttpStatusCode
import io.ktor.http.takeFrom

suspend fun <R> execute(f: suspend () -> R): Either<Error, R> =
    try {
        Either.Right(f())
    } catch (e: Exception) {
        // print(e.toString())
        Either.Left(
            when (e) {
                is ClientRequestException -> when (e.response.status) {
                    HttpStatusCode.NotFound -> Error.NotFound
                    HttpStatusCode.BadRequest -> Error.NoInternet
                    else -> Error.Default
                }
                else -> Error.Default
            }
        )
    }

fun HttpRequestBuilder.apiUrl(path: String) {
    println("$path&appid=${Constants.API_KEY}&units=metric")
    url {
        takeFrom(Constants.END_POINT)
        encodedPath = "$path&appid=${Constants.API_KEY}&units=metric"
    }
}
