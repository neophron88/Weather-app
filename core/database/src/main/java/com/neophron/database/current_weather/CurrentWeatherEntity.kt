package com.neophron.database.current_weather

import androidx.room.Entity
import com.neophron.database.base.BaseWeatherEntity

@Entity(tableName = "current_weather_table")
class CurrentWeatherEntity(
    id: Long,
    cityName: String,
    temperature: Double,
    tempMin: Double,
    tempMax: Double,
    feelsLike: Double,
    pressure: Int,
    humidity: Int,
    windSpeed: Double,
    description: String,
    iconUrl: String,
    timeUnix: Long
) : BaseWeatherEntity(
    id,
    cityName,
    temperature,
    tempMin,
    tempMax,
    feelsLike,
    pressure,
    humidity,
    windSpeed,
    description,
    iconUrl,
    timeUnix
)
