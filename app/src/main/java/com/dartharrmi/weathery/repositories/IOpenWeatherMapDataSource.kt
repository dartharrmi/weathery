package com.dartharrmi.weathery.repositories

import android.graphics.Bitmap
import com.dartharrmi.weathery.domain.CityWeather
import com.dartharrmi.weathery.domain.responses.FindCitiesResponse
import com.dartharrmi.weathery.webservice.api.IOpenWeatherMapApi
import com.dartharrmi.weathery.webservice.dto.responses.FindCitiesResponseDTO

interface IOpenWeatherMapDataSource {

    interface Local {

        suspend fun getBookmarked(): List<CityWeather>

        suspend fun saveBookmark(cityWeather: CityWeather): List<Long>
    }

    interface Remote {

        fun openWeatherMapApi(): IOpenWeatherMapApi

        suspend fun findCitiesByName(query: String): FindCitiesResponseDTO

        suspend fun findCitiesByLocation(lat: Double, lng: Double): FindCitiesResponseDTO

        suspend fun downloadIcon(url: String): Bitmap?
    }

    interface Repository {

        suspend fun findCitiesByName(query: String): FindCitiesResponse

        suspend fun findCitiesByLocation(lat: Double, lng: Double): FindCitiesResponse

        suspend fun downloadIcon(url: String): Bitmap?

        suspend fun getBookmarked(): List<CityWeather>

        suspend fun saveBookmark(cityWeather: CityWeather): Boolean
    }
}