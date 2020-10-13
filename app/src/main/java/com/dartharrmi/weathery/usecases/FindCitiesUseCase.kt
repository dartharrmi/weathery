package com.dartharrmi.weathery.usecases

import com.dartharrmi.weathery.domain.CityWeather
import com.dartharrmi.weathery.repositories.IOpenWeatherMapDataSource.Repository

interface IFindCitiesUseCase {
    suspend fun execute(query: String): List<CityWeather>
}

class FindCitiesUseCase(private val repository: Repository) : IFindCitiesUseCase {

    override suspend fun execute(query: String): List<CityWeather> {
        val result = repository.findCities(query)

        if (result.count == 0) {
            throw Exception()
        }

        return result.cities
    }
}