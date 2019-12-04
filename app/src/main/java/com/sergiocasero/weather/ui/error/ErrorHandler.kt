package com.sergiocasero.weather.ui.error

import com.sergiocasero.weather.domain.Error

actual class ErrorHandler {
    actual fun convert(error: Error): String = when (error) {
        else -> "error"
    }
}
