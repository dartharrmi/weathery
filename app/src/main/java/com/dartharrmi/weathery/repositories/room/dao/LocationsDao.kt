package com.dartharrmi.weathery.repositories.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dartharrmi.weathery.domain.CityWeather

@Dao
interface LocationsDao {

    @Query("SELECT * FROM locations")
    suspend fun getAll(): List<CityWeather>

    @Query("SELECT * FROM locations WHERE name LIKE :name LIMIT 1")
    suspend fun findByName(name: String): CityWeather

    @Insert
    suspend fun insertAll(vararg locations: CityWeather)

    @Delete
    suspend fun delete(location: CityWeather)

}