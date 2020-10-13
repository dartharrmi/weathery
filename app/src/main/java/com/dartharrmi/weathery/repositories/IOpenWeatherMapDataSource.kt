package com.dartharrmi.weathery.repositories

import com.dartharrmi.weathery.domain.CityWeather
import com.dartharrmi.weathery.domain.responses.FindCitiesResponse
import com.dartharrmi.weathery.webservice.api.IOpenWeatherMapApi
import com.dartharrmi.weathery.webservice.dto.responses.FindCitiesResponseDTO

interface IOpenWeatherMapDataSource {

    interface Local {

        fun getBookmarked(): List<CityWeather>

        fun saveBookmark(cityWeather: CityWeather)
    }

    interface Remote {

        fun openWeatherMapApi(): IOpenWeatherMapApi

        suspend fun findCitiesByName(query: String): FindCitiesResponseDTO

        suspend fun findCitiesByLocation(lat: Double, lng: Double): FindCitiesResponseDTO
    }

    interface Repository {

        suspend fun findCitiesByName(query: String): FindCitiesResponse

        suspend fun findCitiesByLocation(lat: Double, lng: Double): FindCitiesResponse

        fun getBookmarked(): List<CityWeather>

        fun saveBookmark(cityWeather: CityWeather)
    }
}