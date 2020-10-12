package com.dartharrmi.weathery.repositories

import android.content.Context
import com.dartharrmi.weathery.repositories.IOpenWeatherMapDataSource.Remote
import com.dartharrmi.weathery.webservice.dto.responses.FindCitiesResponseDTO
import com.dartharrmi.weathery.webservice.utils.IPublicOkHttpClient
import com.dartharrmi.weathery.webservice.utils.WebServiceFactory

class OpenWeatherMapRemoteDataSource(private val context: Context,
                                     private val httpClient: IPublicOkHttpClient) : Remote {

    override fun openWeatherMapApi() = WebServiceFactory.createIOpenWeatherMapApi(context, httpClient)

    override fun findCities(query: String) = openWeatherMapApi().searchCities(query)
}