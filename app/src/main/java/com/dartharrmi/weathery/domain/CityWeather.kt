package com.dartharrmi.weathery.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CityWeather(val lat: Double,
                       val lon: Double,
                       val id: Long,
                       val name: String,
                       val temperature: Double,
                       val feelsLike: Double,
                       val humidity: Double,
                       val windSpeed: Double,
                       val windDegree: Double,
                       val weatherHeader: String,
                       val weatherDescription: String,
                       val weatherIcon: String,
                       var bookmarked: Boolean = false): Parcelable