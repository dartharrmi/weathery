package com.dartharrmi.weathery.webservice.dto

import com.dartharrmi.weathery.domain.CityWeather

data class CityWeatherDTO(
    val lat: Double?,
    val lon: Double?,
    val id: Long?,
    val name: String?,
    val temperature: Double?,
    val feelsLike: Double?,
    val humidity: Double?,
    val windSpeed: Double?,
    val windDegree: Double?,
    val weatherHeader: String?,
    val weatherDescription: String?,
    val weatherIcon: String?
)

fun CityWeatherDTO.toDomain(): CityWeather = CityWeather(
    lat = this.lat ?: 0.0,
    lon = this.lon ?: 0.0,
    id = this.id ?: 0,
    name = this.name.orEmpty(),
    temperature = this.temperature ?: 0.0,
    feelsLike = this.feelsLike ?: 0.0,
    humidity = this.humidity ?: 0.0,
    windSpeed = this.windSpeed ?: 0.0,
    windDegree = this.windDegree ?: 0.0,
    weatherHeader = this.weatherHeader.orEmpty(),
    weatherDescription = this.weatherDescription.orEmpty(),
    weatherIcon = this.weatherIcon.orEmpty()
)