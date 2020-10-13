package com.dartharrmi.weathery.repositories.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dartharrmi.weathery.domain.CityWeather
import com.dartharrmi.weathery.repositories.room.dao.LocationsDao

@Database(entities = [CityWeather::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationsDao(): LocationsDao
}