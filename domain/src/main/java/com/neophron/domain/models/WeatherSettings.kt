package com.neophron.domain.models

sealed class WeatherSettings {
    data class CityParam(val name: String?) : WeatherSettings()
    data class TempUnitParam(val unit: TempUnit) : WeatherSettings()
    data class LanguageParam(val lang: Language) : WeatherSettings()
}

fun WeatherSettings.string() =
    when (this) {
        is WeatherSettings.CityParam -> name
        is WeatherSettings.TempUnitParam -> unit.name
        is WeatherSettings.LanguageParam -> lang.name
    }
