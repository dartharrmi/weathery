package com.dartharrmi.weathery.repositories

import android.content.Context
import com.dartharrmi.weathery.repositories.IOpenWeatherMapDataSource.Remote
import com.dartharrmi.weathery.webservice.utils.IPublicOkHttpClient
import com.dartharrmi.weathery.webservice.utils.WebServiceFactory

class OpenWeatherMapRemoteDataSource(private val context: Context,
                                     private val httpClient: IPublicOkHttpClient): Remote {

    override fun openWeatherMapApi() = WebServiceFactory.createIOpenWeatherMapApi(context, httpClient)

    override suspend fun findCitiesByName(query: String) = openWeatherMapApi().searchCitiesByName(query)

    override suspend fun findCitiesByLocation(lat: Double, lng: Double) = openWeatherMapApi().searchCitiesByLocation(lat, lng)
}