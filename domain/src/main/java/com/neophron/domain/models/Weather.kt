package com.neophron.domain.models

import java.time.LocalDateTime

data class Weather(
    val cityName: String,
    val temperature: Double,
    val tempMin: Double,
    val tempMax: Double,
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    val windSpeed: Double,
    val description: String,
    val iconUrl: String,
    val time: LocalDateTime
)
