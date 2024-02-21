package com.neophron.network.base

import com.squareup.moshi.Json


class DescriptionDTO(
    @field:Json(name = "description")
    val description: String,
    @field:Json(name = "icon")
    val icon: String
)

class WeatherInfoDTO(
    @field:Json(name = "temp")
    val temperature: Double,
    @field:Json(name = "feels_like")
    val feelsLike: Double,
    @field:Json(name = "temp_min")
    val tempMin: Double,
    @field:Json(name = "temp_max")
    val tempMax: Double,
    @field:Json(name = "pressure")
    val pressure: Int,
    @field:Json(name = "humidity")
    val humidity: Int
)

class WindDTO(
    @field:Json(name = "speed")
    val speed: Double,
)

class CityDTO(
    @field:Json(name = "name")
    val name: String,
)

object TempUnitApi {
    const val farenheit = "imperial"
    const val celsius = "metric"
}

object LanguageApi {
    const val russian = "ru"
    const val english = "en"
}