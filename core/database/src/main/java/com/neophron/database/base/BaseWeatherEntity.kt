package com.neophron.database.base

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

abstract class BaseWeatherEntity(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "city_name") val cityName: String,
    @ColumnInfo(name = "temperature") val temperature: Double,
    @ColumnInfo(name = "temp_min") val tempMin: Double,
    @ColumnInfo(name = "temp_max") val tempMax: Double,
    @ColumnInfo(name = "feels_like") val feelsLike: Double,
    @ColumnInfo(name = "pressure") val pressure: Int,
    @ColumnInfo(name = "humidity") val humidity: Int,
    @ColumnInfo(name = "wind_speed") val windSpeed: Double,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "icon_url") val iconUrl: String,
    @ColumnInfo(name = "time_unix") val timeUnix: Long
)
