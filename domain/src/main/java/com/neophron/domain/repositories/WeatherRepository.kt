package com.neophron.domain.repositories

import com.neophron.domain.models.Weather
import com.neophron.domain.models.WeatherSettings
import com.neophron.domain.results.WeatherResult
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    fun getCurrentWeather(): Flow<WeatherResult<Weather>>

    fun getDaysWeather(): Flow<WeatherResult<List<Weather>>>

    suspend fun changeWeatherSettings(settings: WeatherSettings): WeatherResult<Unit>
}