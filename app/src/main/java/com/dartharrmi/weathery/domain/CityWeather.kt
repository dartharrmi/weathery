package com.dartharrmi.weathery.domain

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "locations")
data class CityWeather(@ColumnInfo val lat: Double,
                       @ColumnInfo val lon: Double,
                       @PrimaryKey val id: Long,
                       @ColumnInfo val name: String,
                       @ColumnInfo val temperature: Double,
                       @ColumnInfo val feelsLike: Double,
                       @ColumnInfo val humidity: Double,
                       @ColumnInfo val windSpeed: Double,
                       @ColumnInfo val windDegree: Double,
                       @ColumnInfo val weatherHeader: String,
                       @ColumnInfo val weatherDescription: String,
                       @ColumnInfo val weatherIcon: String,
                       @ColumnInfo var bookmarked: Boolean = false): Parcelable