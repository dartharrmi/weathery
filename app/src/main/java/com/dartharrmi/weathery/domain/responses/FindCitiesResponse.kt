package com.dartharrmi.weathery.domain.responses

import com.dartharrmi.weathery.domain.CityWeather

data class FindCitiesResponse(val count: Int,
                              val cities: List<CityWeather>,
                              val message: String)
