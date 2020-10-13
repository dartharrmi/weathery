package com.dartharrmi.weathery.webservice.dto.responses

import com.dartharrmi.weathery.domain.responses.FindCitiesResponse
import com.dartharrmi.weathery.webservice.dto.CityWeatherDTO
import com.dartharrmi.weathery.webservice.dto.toDomain
import com.google.gson.annotations.SerializedName

data class FindCitiesResponseDTO(val count: Int?,
                                 @SerializedName("list") val cities: List<CityWeatherDTO>?,
                                 val message: String?)

fun FindCitiesResponseDTO.toDomain() = FindCitiesResponse(
    count = this.count ?: 0,
    cities = this.cities?.map { it.toDomain() } ?: emptyList(),
    message = this.message.orEmpty()
)
