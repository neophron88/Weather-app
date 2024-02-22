package com.neophron.data.base

import com.neophron.database.base.AUTO_GENERATE
import com.neophron.database.current_weather.CurrentWeatherEntity
import com.neophron.database.several_days_weather.DayWeatherEntity
import com.neophron.domain.models.Language
import com.neophron.domain.models.TempUnit
import com.neophron.domain.models.Weather
import com.neophron.domain.models.WeatherSettings
import com.neophron.network.base.Icon
import com.neophron.network.base.LanguageApi
import com.neophron.network.base.TempUnitApi
import com.neophron.network.current_weather.CurrentWeatherResponse
import com.neophron.network.several_days_weather.DayWeatherDTO
import com.neophron.network.several_days_weather.DaysWeatherDTOResponse
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit


fun CurrentWeatherResponse.toEntity() = CurrentWeatherEntity(
    id = AUTO_GENERATE,
    cityName = this.cityName,
    temperature = this.info.temperature,
    tempMin = this.info.tempMin,
    tempMax = this.info.tempMax,
    feelsLike = this.info.feelsLike,
    pressure = this.info.pressure,
    humidity = this.info.humidity,
    windSpeed = this.wind.speed,
    description = this.description[0].description,
    iconUrl = this.description[0].icon.toUrl(),
    timeUnix = this.timeInUnix
)

fun DaysWeatherDTOResponse.toEntities() =
    listOfWeatherByHourAndDay.map {
        it.toEntity(this.city.name) }

fun DayWeatherDTO.toEntity(city:String) = DayWeatherEntity(
    id = AUTO_GENERATE,
    cityName = city,
    temperature = this.info.temperature,
    tempMin = this.info.tempMin,
    tempMax = this.info.tempMax,
    feelsLike = this.info.feelsLike,
    pressure = this.info.pressure,
    humidity = this.info.humidity,
    windSpeed = this.wind.speed,
    description = this.description[0].description,
    iconUrl = this.description[0].icon.toUrl(),
    timeUnix = this.timeInUnix
)

fun DayWeatherEntity.toDomain() = Weather(
    cityName = this.cityName,
    temperature = this.temperature,
    tempMin = this.tempMin,
    tempMax = this.tempMax,
    feelsLike = this.feelsLike,
    pressure = this.pressure,
    humidity = this.humidity,
    windSpeed = this.windSpeed,
    description = this.description,
    iconUrl = this.iconUrl,
    time = this.timeUnix.toLocalDateTime()
)


fun List<CurrentWeatherEntity>.toDomain() = map { it.toDomain() }

fun CurrentWeatherEntity.toDomain() = Weather(
    cityName = this.cityName,
    temperature = this.temperature,
    tempMin = this.tempMin,
    tempMax = this.tempMax,
    feelsLike = this.feelsLike,
    pressure = this.pressure,
    humidity = this.humidity,
    windSpeed = this.windSpeed,
    description = this.description,
    iconUrl = this.iconUrl,
    time = this.timeUnix.toLocalDateTime()
)

fun WeatherSettings.TempUnitParam.toApiRepresentation() =
    when (this.unit) {
        TempUnit.Fahrenheit -> TempUnitApi.farenheit
        TempUnit.Celsius -> TempUnitApi.celsius
        else -> null
    }

fun WeatherSettings.LanguageParam.toApiRepresentation() =
    when (this.lang) {
        Language.English -> LanguageApi.english
        else -> LanguageApi.russian

    }


fun Long.toLocalDateTime(): LocalDateTime {
    val inMillis = TimeUnit.SECONDS.toMillis(this)
    return Instant.ofEpochMilli(inMillis).atZone(ZoneId.systemDefault()).toLocalDateTime();
}

fun String.toUrl(): String = Icon.url.replace(Icon.placeHolder, this)
