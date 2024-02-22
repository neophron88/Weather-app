package com.neophron.main.domain.models

import com.neophron.domain.models.TempUnit

data class CurrentWeatherDisplay(
    val cityName: String,
    val temperature: String,
    val tempMin: String,
    val tempMax: String,
    val feelsLike: String,
    val pressure: String,
    val humidity: String,
    val windSpeed: String,
    val description: String,
    val iconUrl: String,
    val unit: TempUnit
)

