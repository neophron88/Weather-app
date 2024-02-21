package com.neophron.database.several_days_weather

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class DaysWeatherDao {

    @Query("SELECT * FROM day_weather_table")
    abstract fun fetchDaysWeather(): Flow<List<DayWeatherEntity>>

    @Transaction
    open suspend fun refreshDaysWeather(entities: List<DayWeatherEntity>) {
        deleteDaysWeather()
        insertDaysWeather(entities)
    }

    @Query("DELETE FROM today_weather_table")
    internal abstract suspend fun deleteDaysWeather()

    @Insert
    internal abstract suspend fun insertDaysWeather(products: List<DayWeatherEntity>)

}