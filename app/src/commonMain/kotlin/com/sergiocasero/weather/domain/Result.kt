package com.sergiocasero.weather.domain

sealed class Error {
    object NoInternet : Error()
    object NotFound : Error()
    object Default : Error()
}

object Success
