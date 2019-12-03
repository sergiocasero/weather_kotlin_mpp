package com.sergiocasero.weather.ui.executor

import kotlinx.coroutines.CoroutineDispatcher

expect class Executor {
    val main: CoroutineDispatcher
}
