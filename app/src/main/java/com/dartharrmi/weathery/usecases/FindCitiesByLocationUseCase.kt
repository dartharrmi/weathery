package com.dartharrmi.weathery.usecases

import com.dartharrmi.weathery.domain.CityWeather
import com.dartharrmi.weathery.repositories.IOpenWeatherMapDataSource.Repository

interface IFindCitiesByLocationUseCase {

    suspend fun execute(lat: Double, lng: Double): List<CityWeather>
}

class FindCitiesByLocationUseCase(private val repository: Repository): IFindCitiesByLocationUseCase {

    override suspend fun execute(lat: Double, lng: Double): List<CityWeather> {
        val result = repository.findCitiesByLocation(lat, lng)

        if (result.count == 0) {
            throw Exception()
        }

        return result.cities
    }
}