package com.sergiocasero.weather.ui.error

import com.sergiocasero.weather.domain.Error

expect class ErrorHandler {
    fun convert(error: Error): String
}
