package com.dartharrmi.weathery.repositories

import android.content.Context
import com.dartharrmi.weathery.domain.CityWeather
import com.dartharrmi.weathery.repositories.IOpenWeatherMapDataSource.Local
import com.dartharrmi.weathery.repositories.IOpenWeatherMapDataSource.Remote
import com.dartharrmi.weathery.webservice.dto.responses.FindCitiesResponseDTO
import com.dartharrmi.weathery.webservice.utils.IPublicOkHttpClient
import com.dartharrmi.weathery.webservice.utils.WebServiceFactory

class OpenWeatherMapLocalDataSource(private val context: Context) : Local {

    override fun getBookmarked(): List<CityWeather> = emptyList()

    override fun saveBookmark(cityWeather: CityWeather) {
    }
}