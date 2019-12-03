package com.sergiocasero.weather.ui.presenter

import com.sergiocasero.weather.domain.Error
import com.sergiocasero.weather.ui.error.ErrorHandler
import com.sergiocasero.weather.ui.executor.Executor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

abstract class Presenter<out V : Presenter.View>(
    protected val errorHandler: ErrorHandler,
    executor: Executor,
    val view: V
) {

    private val job = SupervisorJob()

    protected val scope = CoroutineScope(job + executor.main)

    protected fun onRetry(error: Error, retry: () -> Unit): Unit =
        view.showRetry(errorHandler.convert(error)) { retry() }

    abstract fun attach()

    fun detach() = job.cancel()

    interface View {
        fun showProgress()
        fun hideProgress()
        fun showRetry(error: String, f: () -> Unit)
    }
}

