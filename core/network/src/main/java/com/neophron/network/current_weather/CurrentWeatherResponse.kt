package com.neophron.network.current_weather

import com.neophron.network.base.DescriptionDTO
import com.neophron.network.base.WeatherInfoDTO
import com.neophron.network.base.WindDTO
import com.squareup.moshi.Json

class CurrentWeatherResponse(
    @field:Json(name = "weather")
    val description: List<DescriptionDTO>,
    @field:Json(name = "main")
    val info: WeatherInfoDTO,
    @field:Json(name = "wind")
    val wind: WindDTO,
    @field:Json(name = "name")
    val cityName: String,
    @field:Json(name = "dt")
    val timeInUnix: Long,
)

