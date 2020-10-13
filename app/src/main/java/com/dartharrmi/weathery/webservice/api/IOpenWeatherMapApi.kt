package com.dartharrmi.weathery.webservice.api

import com.dartharrmi.weathery.webservice.dto.responses.FindCitiesResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface for defining the methods to communicate with the Spoonacular API.
 */
interface IOpenWeatherMapApi {

    private companion object {

        const val FIND = "find/"
        const val WEATHER = "weather"

        const val PARAM_QUERY = "q"
        const val PARAM_LAT = "lat"
        const val PARAM_LNG = "lon"
    }

    @GET(FIND)
    suspend fun searchCitiesByName(@Query(PARAM_QUERY) query: String): FindCitiesResponseDTO

    @GET(FIND)
    suspend fun searchCitiesByLocation(@Query(PARAM_LAT) latitude: String, @Query(PARAM_LNG) longitude: String): FindCitiesResponseDTO
}