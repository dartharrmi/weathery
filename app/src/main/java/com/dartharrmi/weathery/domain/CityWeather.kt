package com.dartharrmi.weathery.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CityWeather(private val lat: Double,
                       private val lon: Double,
                       private val id: Long,
                       private val name: String,
                       private val temperature: Double,
                       private val feelsLike: Double,
                       private val humidity: Double,
                       private val rainChances: Double,
                       private val windSpeed: Double,
                       private val windDegree: Double): Parcelable