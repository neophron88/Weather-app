package com.neophron.main.domain.models


data class WeatherByHourDisplayContainer(
    val dayDate: String,
    val display: List<WeatherByHourDisplay>
): DisplayWeather()

data class WeatherByHourDisplay(
    val temperature: String,
    val iconUrl: String,
    val time: String,
)
