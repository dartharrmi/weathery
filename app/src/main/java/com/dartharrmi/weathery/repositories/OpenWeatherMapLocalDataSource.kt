package com.dartharrmi.weathery.repositories

import com.dartharrmi.weathery.domain.CityWeather
import com.dartharrmi.weathery.repositories.IOpenWeatherMapDataSource.Local
import com.dartharrmi.weathery.repositories.room.AppDatabase

class OpenWeatherMapLocalDataSource(private val db: AppDatabase) : Local {

    override suspend fun getBookmarked(): List<CityWeather> = db.locationsDao().getAll()

    override suspend fun saveBookmark(cityWeather: CityWeather) = db.locationsDao().insertAll(cityWeather)
}