package com.neophron.main.domain.models


data class WeatherByPeriodDisplay(
    val time: String,
    val midnight: DayPeriodDisplay?,
    val morning: DayPeriodDisplay?,
    val afternoon: DayPeriodDisplay?,
    val night: DayPeriodDisplay?
) : DisplayWeather()

data class DayPeriodDisplay(
    val temperature: String,
    val iconUrl: String,
    val windSpeed: String,
)
