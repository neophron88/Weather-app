package com.neophron.main.ui

import com.neophron.domain.models.Weather
import com.neophron.main.domain.models.DisplayWeather

suspend fun mapToDisplay(
    current: Weather,
    days: List<Weather>
): List<DisplayWeather> {

}