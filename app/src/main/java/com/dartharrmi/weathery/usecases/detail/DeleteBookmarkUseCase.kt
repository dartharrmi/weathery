package com.dartharrmi.weathery.usecases.detail

import com.dartharrmi.weathery.domain.CityWeather
import com.dartharrmi.weathery.repositories.IOpenWeatherMapDataSource.Repository

interface IDeleteBookmarkUseCase {

    suspend fun execute(location: CityWeather): Boolean
}

class DeleteBookmarkUseCase(private val repository: Repository) : IDeleteBookmarkUseCase {

    override suspend fun execute(location: CityWeather) = repository.removeBookmark(location)
}