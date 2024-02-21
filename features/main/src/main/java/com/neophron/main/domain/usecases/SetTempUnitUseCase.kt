package com.neophron.main.domain.usecases

import com.neophron.domain.models.TempUnit
import com.neophron.domain.models.WeatherSettings
import com.neophron.domain.repositories.WeatherPreference
import com.neophron.domain.results.WeatherSingleResult

class SetTempUnitUseCase(
    private val preference: WeatherPreference
) {

    suspend operator fun invoke(unit: TempUnit): WeatherSingleResult<Unit> {
        preference.saveTempUnit(WeatherSettings.TempUnitParam(unit))
        return WeatherSingleResult.Success(Unit)
    }
}