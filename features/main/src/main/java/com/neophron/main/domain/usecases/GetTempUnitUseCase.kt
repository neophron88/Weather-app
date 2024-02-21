package com.neophron.main.domain.usecases

import com.neophron.domain.models.TempUnit
import com.neophron.domain.repositories.WeatherPreference

class GetTempUnitUseCase(
    private val preference: WeatherPreference
) {

    suspend operator fun invoke(): TempUnit =
        preference.getTempUnit().unit
}