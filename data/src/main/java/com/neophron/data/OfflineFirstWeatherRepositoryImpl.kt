package com.neophron.data

import com.neophron.data.base.runCatch
import com.neophron.data.base.toApiRepresentation
import com.neophron.data.base.toDomain
import com.neophron.data.base.toEntities
import com.neophron.data.base.toEntity
import com.neophron.data.base.toErrorType
import com.neophron.database.current_weather.CurrentWeatherDao
import com.neophron.database.several_days_weather.DaysWeatherDao
import com.neophron.domain.errors.ErrorType
import com.neophron.domain.models.Weather
import com.neophron.domain.models.WeatherSettings
import com.neophron.domain.repositories.WeatherPreference
import com.neophron.domain.repositories.WeatherRepository
import com.neophron.domain.results.WeatherResult
import com.neophron.network.current_weather.CurrentWeatherService
import com.neophron.network.several_days_weather.SeveralDaysWeatherService
import com.neophron88.library.ktx.require
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.sync.Mutex

class OfflineFirstWeatherRepositoryImpl(
    private val currentWeatherService: CurrentWeatherService,
    private val currentWeatherDao: CurrentWeatherDao,
    private val daysWeatherService: SeveralDaysWeatherService,
    private val daysWeatherDao: DaysWeatherDao,
    private val preference: WeatherPreference,
    private val applicationScope: CoroutineScope
) : WeatherRepository {

    override fun getCurrentWeather(): Flow<WeatherResult<Weather>> = flow {
        emit(WeatherResult.Pending())
        val cityName = preference.getCity().name
        if (cityName != null) runCatch(
            run = {
                val response = currentWeatherService.getWeatherByCityName(
                    cityName,
                    preference.getTempUnit().toApiRepresentation(),
                    preference.getLanguage().toApiRepresentation()
                )
                currentWeatherDao.refreshCurrrentWeather(listOf(response.toEntity()))

            },
            failed = { emit(WeatherResult.Error(it.toErrorType())) }
        )

        currentWeatherDao.fetchCurrrentWeather().collect {
            val result =
                if (it.isNotEmpty()) WeatherResult.Success(it.toDomain()[0])
                else WeatherResult.Error(ErrorType.EmptyList)
            emit(result)
        }
    }

    override fun getDaysWeather(): Flow<WeatherResult<List<Weather>>> = flow {
        emit(WeatherResult.Pending())
        val cityName = preference.getCity().name
        if (cityName != null) runCatch(
            run = {
                val response = daysWeatherService.getWeatherByCityName(
                    cityName,
                    preference.getTempUnit().toApiRepresentation(),
                    preference.getLanguage().toApiRepresentation()
                )
                daysWeatherDao.refreshDaysWeather(response.toEntities())
            },
            failed = { emit(WeatherResult.Error(it.toErrorType())) }
        )

        daysWeatherDao.fetchDaysWeather().collect { list ->
            val result =
                if (list.isNotEmpty()) WeatherResult.Success(list.map { it.toDomain() })
                else WeatherResult.Error(ErrorType.EmptyList)
            emit(result)
        }
    }

    override suspend fun changeWeatherSettings(settings: WeatherSettings): WeatherResult<Unit> =
        when (settings) {
            is WeatherSettings.CityParam -> updateCity(settings.name)
            is WeatherSettings.TempUnitParam -> updateUnit(settings)
            else -> WeatherResult.Success(Unit)
        }

    private suspend fun updateCity(cityName: String?): WeatherResult<Unit> {
        if (cityName.isNullOrBlank()) error("cityName is required")
        var result: WeatherResult<Unit> = WeatherResult.Success(Unit)
        runCatch(
            run = {
                applicationScope.async {
                    fetchData(
                        cityName,
                        preference.getTempUnit().toApiRepresentation(),
                        preference.getLanguage().toApiRepresentation()
                    )
                    preference.saveCity(WeatherSettings.CityParam(cityName))
                }.await()
            },
            failed = { result = WeatherResult.Error(it.toErrorType()) }
        )
        return result
    }

    private suspend fun updateUnit(unit: WeatherSettings.TempUnitParam): WeatherResult<Unit> {
        var result: WeatherResult<Unit> = WeatherResult.Success(Unit)
        runCatch(
            run = {
                applicationScope.async {
                    fetchData(
                        preference.getCity().name.require(),
                        unit.toApiRepresentation(),
                        preference.getLanguage().toApiRepresentation()
                    )
                    preference.saveTempUnit(unit)
                }.await()
            },
            failed = { result = WeatherResult.Error(it.toErrorType()) }
        )
        return result
    }

    private suspend fun fetchData(
        cityName: String,
        units: String?,
        lang: String,
    ) {

        val currentResponse = currentWeatherService.getWeatherByCityName(cityName, units, lang)
        currentWeatherDao.refreshCurrrentWeather(listOf(currentResponse.toEntity()))

        val daysResponse = daysWeatherService.getWeatherByCityName(cityName, units, lang)
        daysWeatherDao.refreshDaysWeather(daysResponse.toEntities())
    }

}