package com.dartharrmi.weathery.repositories

import android.graphics.Bitmap
import com.dartharrmi.weathery.domain.CityWeather
import com.dartharrmi.weathery.repositories.IOpenWeatherMapDataSource.Local
import com.dartharrmi.weathery.repositories.IOpenWeatherMapDataSource.Remote
import com.dartharrmi.weathery.repositories.IOpenWeatherMapDataSource.Repository
import com.dartharrmi.weathery.webservice.dto.responses.toDomain

class WeatherRepository(private val localDataSource: Local,
                        private val remote: Remote): Repository {

    override suspend fun findCitiesByName(query: String) = remote.findCitiesByName(query).toDomain()

    override suspend fun findCitiesByLocation(lat: Double, lng: Double) = remote.findCitiesByLocation(lat, lng).toDomain()

    override fun getBookmarked() = localDataSource.getBookmarked()

    override fun saveBookmark(cityWeather: CityWeather) = localDataSource.saveBookmark(cityWeather)

    override suspend fun downloadIcon(url: String) = remote.downloadIcon(url)
}