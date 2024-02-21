package com.neophron.main.domain.models

import com.neophron.domain.models.Weather

data class CurrentWeather(
    val weather: Weather
):DisplayWeather()

