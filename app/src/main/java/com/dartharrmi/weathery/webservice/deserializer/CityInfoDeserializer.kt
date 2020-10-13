package com.dartharrmi.weathery.webservice.deserializer

import com.dartharrmi.weathery.utils.Logger
import com.dartharrmi.weathery.webservice.dto.CityWeatherDTO
import com.dartharrmi.weathery.webservice.utils.ICON_URL
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

/**
 * Custom deserializer for parsing a list of cities.
 *
 */
class CityInfoDeserializer : JsonDeserializer<List<CityWeatherDTO>> {

    companion object {
        /**
         * OK, this a tricky one: Kotlin compiler sees List like a covariant generic type List<out T> and compiles its instantiations as wildcard types,
         * i. e. List<CityWeatherDTO> gets compiled as List<? extends CityWeatherDTO>.
         *
         * So, in order for the deserializer to be able to parse the list, the type for the TypeToken needs to be a
         * [MutableList], which is actually invariant.
         *
         * Many thanks, variance from Java.
         */
        val typeToken: Type = object : TypeToken<MutableList<CityWeatherDTO>>() {}.type

        val tag = Logger.makeLogTag("Deserializer")
    }

    override fun deserialize(json: JsonElement,
                             typeOfT: Type,
                             context: JsonDeserializationContext): List<CityWeatherDTO> {
        return try {
            if (json.asJsonArray.size() == 0) {
                emptyList()
            } else {
                json.asJsonArray.map { jsonElement ->
                    with(jsonElement.asJsonObject) {
                        CityWeatherDTO(
                            lat = this.getAsJsonObject("coord").get("lat").asDouble,
                            lon = this.getAsJsonObject("coord").get("lon").asDouble,
                            id = this.get("id").asLong,
                            name = this.get("name").asString,
                            temperature = this.getAsJsonObject("main").get("temp").asDouble,
                            feelsLike = this.getAsJsonObject("main").get("feels_like").asDouble,
                            humidity = this.getAsJsonObject("main").get("humidity").asDouble,
                            windSpeed = this.getAsJsonObject("wind").get("speed").asDouble,
                            windDegree = this.getAsJsonObject("wind").get("deg").asDouble,
                            weatherHeader = (this.getAsJsonArray("weather").get(0) as JsonObject).get("main").asString,
                            weatherDescription = (this.getAsJsonArray("weather").get(0) as JsonObject).get("description").asString,
                            weatherIcon = String.format(ICON_URL, (this.getAsJsonArray("weather").get(0) as JsonObject).get("icon").asString)
                        )
                    }
                }.toList()
            }
        } catch (e: Exception) {
            Logger.LOGE(tag, "Error parsing the cities array", e)
            emptyList()
        }
    }
}