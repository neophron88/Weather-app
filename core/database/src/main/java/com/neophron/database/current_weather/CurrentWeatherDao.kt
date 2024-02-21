package com.neophron.database.current_weather

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CurrentWeatherDao {

    @Query("SELECT * FROM current_weather_table")
    abstract fun fetchCurrrentWeather(): Flow<List<CurrentWeatherEntity>>

    @Transaction
    open suspend fun refreshCurrrentWeather(entities: List<CurrentWeatherEntity>) {
        deleteAllCurrrentWeather()
        insertCurrrentWeather(entities)
    }

    @Query("DELETE FROM current_weather_table")
    internal abstract suspend fun deleteAllCurrrentWeather()

    @Insert
    internal abstract suspend fun insertCurrrentWeather(products: List<CurrentWeatherEntity>)

}