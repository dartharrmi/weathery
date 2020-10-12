package com.dartharrmi.weathery.repositories

import com.dartharrmi.weathery.domain.CityWeather
import com.dartharrmi.weathery.repositories.IOpenWeatherMapDataSource.*
import com.dartharrmi.weathery.webservice.dto.responses.toDomain

class WeatherRepository(private val localDataSource: Local,
                        private val remote: Remote): Repository {

    override fun findCities(query: String) = remote.findCities(query).toDomain()

    override fun getBookmarked() = localDataSource.getBookmarked()

    override fun saveBookmark(cityWeather: CityWeather) = localDataSource.saveBookmark(cityWeather)
}