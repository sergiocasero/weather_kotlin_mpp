package com.sergiocasero.weather.data.repository

import com.sergiocasero.weather.data.datasource.local.LocalDataSource
import com.sergiocasero.weather.data.datasource.remote.RemoteDataSource

class CommonRepository(private val local: LocalDataSource, private val remote: RemoteDataSource) : Repository {
}
