package com.neophron.main.ui

import com.neophron.domain.models.TempUnit
import com.neophron.domain.models.Weather
import com.neophron.main.domain.models.CurrentWeatherDisplay
import com.neophron.main.domain.models.DayPeriodDisplay
import com.neophron.main.domain.models.DisplayWeather
import com.neophron.main.domain.models.WeatherByHourDisplay
import com.neophron.main.domain.models.WeatherByHourDisplayContainer
import com.neophron.main.domain.models.WeatherByPeriodDisplay
import com.neophron.main.domain.usecases.GetTempUnitUseCase
import com.neophron.main.utils.StringResProvider
import com.neophron.main.utils.toStringPlaceHolderRes
import com.neophron88.library.ktx.firstCharToUpperCase
import com.neophron88.library.ktx.valueMap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


interface MapperDisplay {
    suspend fun mapToCurrentWeatherDisplay(current: Weather): CurrentWeatherDisplay
    suspend fun mapToDayAndHourDisplay(current: Weather, days: List<Weather>): List<DisplayWeather>
}

class MapperDisplayImpl(
    private val provider: StringResProvider,
    private val getTempUnitUseCase: GetTempUnitUseCase,
    private val dispatcher: CoroutineDispatcher
) : MapperDisplay {

    private lateinit var mCurrent: Weather
    private lateinit var mDays: List<Weather>
    private lateinit var unit: TempUnit


    override suspend fun mapToCurrentWeatherDisplay(current: Weather) = withContext(dispatcher) {
        unit = getTempUnitUseCase.invoke()
        current.valueMap {
            CurrentWeatherDisplay(
                cityName = cityName,
                temperature = temperature.toInt().toString(),
                tempMin = provider.getString(
                    com.neophron.ui.R.string.temperature_placeholder,
                    tempMin.toInt()
                ),
                tempMax = provider.getString(
                    com.neophron.ui.R.string.temperature_placeholder,
                    tempMax.toInt()
                ),
                feelsLike = feelsLike.toInt().toString(),
                pressure = provider.getString(
                    com.neophron.ui.R.string.pressure_placeholder,
                    pressure
                ),
                humidity = provider.getString(
                    com.neophron.ui.R.string.percent_placeholder,
                    humidity
                ),
                windSpeed = provider.getString(
                    com.neophron.ui.R.string.wind_placeholder,
                    windSpeed
                ),
                description = description.firstCharToUpperCase(),
                iconUrl = iconUrl,
                unit = unit
            )
        }
    }


    override suspend fun mapToDayAndHourDisplay(
        current: Weather,
        days: List<Weather>,
    ): List<DisplayWeather> = withContext(dispatcher) {
        unit = getTempUnitUseCase.invoke()
        mCurrent = current
        mDays = days
        listOf(mapToWeatherByHourDisplay()) + mapToWeatherByPeriodDisplayList(days)
    }


    private fun mapToWeatherByHourDisplay() = WeatherByHourDisplayContainer(
        dayDate = DateTimeFormatter.ofPattern("MMM d").format(mCurrent.time).firstCharToUpperCase(),
        display = mapToWeatherByHourDisplayList()
    )

    private fun mapToWeatherByHourDisplayList() = mDays
        .filter { mCurrent.time.dayOfMonth == it.time.dayOfMonth }
        .map {
            WeatherByHourDisplay(
                temperature = provider.getString(unit.toStringPlaceHolderRes(), it.temperature),
                iconUrl = it.iconUrl,
                time = DateTimeFormatter.ofPattern("HH:mm").format(it.time)
            )
        }

    private fun mapToWeatherByPeriodDisplayList(allDaysWeatherByHour: List<Weather>): List<WeatherByPeriodDisplay> {
        val days = mutableListOf<LocalDateTime>()
        var day = mCurrent.time.plusDays(1)
        while (true) {
            val any = allDaysWeatherByHour.any { day.dayOfMonth == it.time.dayOfMonth }
            if (any) {
                days.add(day)
                day = day.plusDays(1)
            } else break
        }


        return days.map { dayOfMonth ->
            val oneDayWeathers =
                allDaysWeatherByHour.filter { dayOfMonth.dayOfMonth == it.time.dayOfMonth }
            WeatherByPeriodDisplay(
                time = DateTimeFormatter.ofPattern("eeee, dd MMMM").format(dayOfMonth)
                    .firstCharToUpperCase(),
                midnight = oneDayWeathers.toPeriod(dayOfMonth, 0, 5),
                morning = oneDayWeathers.toPeriod(dayOfMonth, 8, 12),
                afternoon = oneDayWeathers.toPeriod(dayOfMonth, 12, 17),
                night = oneDayWeathers.toPeriod(dayOfMonth, 19, 23)
            )
        }
    }

    private fun List<Weather>.toPeriod(
        day: LocalDateTime,
        startPeriod: Int,
        endPeriod: Int
    ): DayPeriodDisplay? {
        val start = day.withHour(startPeriod)
        val end = day.withHour(endPeriod)
        val midNightWeather = firstOrNull { it.time > start && it.time <= end }
        return midNightWeather?.let {
            DayPeriodDisplay(
                temperature = provider.getString(unit.toStringPlaceHolderRes(), it.temperature),
                iconUrl = it.iconUrl,
                windSpeed = provider.getString(
                    com.neophron.ui.R.string.wind_placeholder, it.windSpeed
                )
            )
        }
    }
}




