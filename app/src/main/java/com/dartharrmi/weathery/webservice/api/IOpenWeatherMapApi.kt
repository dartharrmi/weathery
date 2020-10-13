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

        const val PARAM_QUERY = "q"
    }

    @GET(FIND)
    suspend fun searchCities(@Query(PARAM_QUERY) query: String): FindCitiesResponseDTO
}