package com.sergiocasero.weather.ui.presenter

import com.sergiocasero.weather.data.repository.Repository
import com.sergiocasero.weather.domain.model.Forecast
import com.sergiocasero.weather.ui.error.ErrorHandler
import com.sergiocasero.weather.ui.executor.Executor
import kotlinx.coroutines.launch

class HomePresenter(
    private val repository: Repository,
    error: ErrorHandler,
    executor: Executor,
    view: HomeView
) :
    Presenter<HomeView>(error, executor, view) {

    override fun attach() {
        view.checkLocationPermission()
    }

    fun onLocationFound(latitude: Double, longitude: Double) {
        scope.launch {
            view.showProgress()
            repository.getLatLngForecast(latitude, longitude).fold(
                error = { onRetry(it) { onLocationFound(latitude, longitude) } },
                success = { view.showForecast(it) }
            )
            view.hideProgress()
        }
    }

    fun onPermissionGranted() {
        view.fetchLocation()
    }
}

interface HomeView : View {
    fun showForecast(weather: List<Forecast>)
    fun checkLocationPermission()
    fun fetchLocation()
}
