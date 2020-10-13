package com.dartharrmi.weathery.usecases

import com.dartharrmi.weathery.domain.CityWeather
import com.dartharrmi.weathery.repositories.IOpenWeatherMapDataSource.Repository

interface IFindCitiesByNameUseCase {

    suspend fun execute(query: String): List<CityWeather>
}

class FindCitiesByNameUseCase(private val repository: Repository): IFindCitiesByNameUseCase {

    override suspend fun execute(query: String): List<CityWeather> {
        val result = repository.findCitiesByName(query)

        if (result.count == 0) {
            throw Exception()
        }

        return result.cities
    }
}