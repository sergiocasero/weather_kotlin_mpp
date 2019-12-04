package com.sergiocasero.weather.ui.activity

import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationServices
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.sergiocasero.weather.R
import com.sergiocasero.weather.data.datasource.local.CommonLocalDataSource
import com.sergiocasero.weather.data.datasource.remote.CommonRemoteDataSource
import com.sergiocasero.weather.data.repository.CommonRepository
import com.sergiocasero.weather.domain.model.Forecast
import com.sergiocasero.weather.ui.adapter.ForecastAdapter
import com.sergiocasero.weather.ui.error.ErrorHandler
import com.sergiocasero.weather.ui.executor.Executor
import com.sergiocasero.weather.ui.extension.hideMe
import com.sergiocasero.weather.ui.extension.retrySnackbar
import com.sergiocasero.weather.ui.extension.showMe
import com.sergiocasero.weather.ui.presenter.HomePresenter
import com.sergiocasero.weather.ui.presenter.HomeView
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), HomeView {

    private val presenter = HomePresenter(
        repository = CommonRepository(local = CommonLocalDataSource(), remote = CommonRemoteDataSource()),
        error = ErrorHandler(),
        executor = Executor(),
        view = this
    )

    private val forecastAdapter = ForecastAdapter()

    private val locationClient by lazy { LocationServices.getFusedLocationProviderClient(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initializeUI()

        presenter.attach()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.detach()
    }

    private fun initializeUI() {
        with(forecast) {
            adapter = forecastAdapter
            layoutManager = LinearLayoutManager(this@HomeActivity)
        }
    }

    override fun showForecast(weather: List<Forecast>) {
        forecastAdapter.addAll(weather)
    }

    override fun showProgress() {
        progress.showMe()
    }

    override fun hideProgress() {
        progress.hideMe()
    }

    override fun showRetry(error: String, f: () -> Unit) {
        retrySnackbar(container, error, f)
    }

    override fun checkLocationPermission() {
        Dexter.withActivity(this)
            .withPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    presenter.onPermissionGranted()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
            .check()
    }

    override fun fetchLocation() {
        locationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                presenter.onLocationFound(it.latitude, it.longitude)
            }
        }
    }
}
