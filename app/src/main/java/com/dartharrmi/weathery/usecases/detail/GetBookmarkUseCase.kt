package com.dartharrmi.weathery.usecases.detail

import com.dartharrmi.weathery.domain.CityWeather
import com.dartharrmi.weathery.repositories.IOpenWeatherMapDataSource.Repository

interface IGetBookmarkUseCase {

    suspend fun execute(): List<CityWeather>
}

class GetBookmarkUseCase(private val repository: Repository) : IGetBookmarkUseCase {

    override suspend fun execute() = repository.getBookmarked()
}