package com.dartharrmi.weathery.usecases.detail

import com.dartharrmi.weathery.domain.CityWeather
import com.dartharrmi.weathery.repositories.IOpenWeatherMapDataSource.Repository

interface ISaveBookmarkUseCase {

    suspend fun execute(location: CityWeather): Boolean
}

class SaveBookmarkUseCase(private val repository: Repository) : ISaveBookmarkUseCase {

    override suspend fun execute(location: CityWeather) = repository.saveBookmark(location)
}