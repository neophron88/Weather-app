package com.neophron.data

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import com.neophron.domain.models.Language
import com.neophron.domain.models.TempUnit
import com.neophron.domain.models.WeatherSettings
import com.neophron.domain.repositories.WeatherPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherPreferencesImpl(
    application: Application
) : WeatherPreference {

    private val preferences = application.getSharedPreferences("account", Context.MODE_PRIVATE)

    override suspend fun getCity(): WeatherSettings.CityParam =
        withContext(Dispatchers.IO) {
            val city = preferences.getString(CITY_KEY, null)
            WeatherSettings.CityParam(city)
        }

    override suspend fun saveCity(settings: WeatherSettings.CityParam) =
        withContext(Dispatchers.IO) {
            preferences.edit(commit = true) {
                putString(CITY_KEY, settings.name)
            }
        }

    override suspend fun getLanguage(): WeatherSettings.LanguageParam =
        withContext(Dispatchers.IO) {
            val langName = preferences.getString(LANGUAGE_KEY, null) ?: Language.Russian.name
            WeatherSettings.LanguageParam(Language.valueOf(langName))
        }

    override suspend fun saveLanguage(settings: WeatherSettings.LanguageParam) =
        withContext(Dispatchers.IO) {
            preferences.edit(commit = true) {
                putString(LANGUAGE_KEY, settings.lang.name)
            }
        }

    override suspend fun getTempUnit(): WeatherSettings.TempUnitParam =
        withContext(Dispatchers.IO) {
            val unitName = preferences.getString(UNIT_KEY, null) ?: TempUnit.Celsius.name
            WeatherSettings.TempUnitParam(TempUnit.valueOf(unitName))
        }

    override suspend fun saveTempUnit(settings: WeatherSettings.TempUnitParam) =
        withContext(Dispatchers.IO) {
            preferences.edit(commit = true) {
                putString(UNIT_KEY, settings.unit.name)
            }
        }


    companion object {
        private const val CITY_KEY = "city"
        private const val LANGUAGE_KEY = "language"
        private const val UNIT_KEY = "unit"
    }
}