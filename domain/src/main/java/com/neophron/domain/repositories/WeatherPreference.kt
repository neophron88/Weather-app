package com.neophron.domain.repositories

import com.neophron.domain.models.WeatherSettings

interface WeatherPreference {

    suspend fun getCity(): WeatherSettings.CityParam

    suspend fun saveCity(settings: WeatherSettings.CityParam)


    suspend fun getLanguage(): WeatherSettings.LanguageParam

    suspend fun saveLanguage(settings: WeatherSettings.LanguageParam)


    suspend fun getTempUnit(): WeatherSettings.TempUnitParam

    suspend fun saveTempUnit(settings: WeatherSettings.TempUnitParam)

}

