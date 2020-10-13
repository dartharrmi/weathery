package com.dartharrmi.weathery.di

import android.content.Context
import com.dartharrmi.weathery.repositories.OpenWeatherMapLocalDataSource
import com.dartharrmi.weathery.repositories.OpenWeatherMapRemoteDataSource
import com.dartharrmi.weathery.repositories.WeatherRepository
import com.dartharrmi.weathery.usecases.FindCitiesUseCase
import com.dartharrmi.weathery.webservice.utils.PublicOkHttpClient

/**
 * Container of objects shared across the whole app
 */
class DependencyContainer(private val context: Context) {

    private val localDataSource = OpenWeatherMapLocalDataSource(context)
    private val remoteDataSource = OpenWeatherMapRemoteDataSource(context, PublicOkHttpClient())

    val repository = WeatherRepository(localDataSource, remoteDataSource)

    val findCitiesUseCase = FindCitiesUseCase(repository)
}