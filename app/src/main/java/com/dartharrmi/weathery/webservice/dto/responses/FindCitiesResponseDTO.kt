package com.dartharrmi.weathery.webservice.dto.responses

import com.dartharrmi.weathery.domain.responses.FindCitiesResponse
import com.dartharrmi.weathery.webservice.dto.CityWeatherDTO
import com.dartharrmi.weathery.webservice.dto.toDomain

data class FindCitiesResponseDTO(val count: Int?,
                                 val cities: List<CityWeatherDTO>?,
                                 val message: String?)

fun FindCitiesResponseDTO.toDomain() = FindCitiesResponse(
    count = this.count ?: 0,
    cities = this.cities?.map { it.toDomain() } ?: emptyList(),
    message = this.message.orEmpty()
)
