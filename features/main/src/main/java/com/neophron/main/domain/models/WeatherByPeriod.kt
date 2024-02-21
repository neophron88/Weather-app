package com.neophron.main.domain.models

import com.neophron.domain.models.Weather


data class WeatherByPeriod(
    val midnight: Weather,
    val morning: Weather,
    val afternoon: Weather,
    val night: Weather
) : DisplayWeather()
