package com.neophron.network.several_days_weather

import com.neophron.network.base.CityDTO
import com.neophron.network.base.DescriptionDTO
import com.neophron.network.base.WeatherInfoDTO
import com.neophron.network.base.WindDTO
import com.squareup.moshi.Json

class DaysWeatherDTOResponse(
    @field:Json(name = "cnt")
    val countOfWeatherByHour: Int,
    @field:Json(name = "list")
    val listOfWeatherByHourAndDay: List<DayWeatherDTO>,
    @field:Json(name = "city")
    val city: CityDTO,
)

class DayWeatherDTO(
    @field:Json(name = "dt")
    val timeInUnix: Long,
    @field:Json(name = "weather")
    val description: List<DescriptionDTO>,
    @field:Json(name = "main")
    val info: WeatherInfoDTO,
    @field:Json(name = "wind")
    val wind: WindDTO,
)
