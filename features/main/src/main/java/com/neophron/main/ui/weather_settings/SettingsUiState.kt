package com.neophron.main.ui.weather_settings

import com.neophron.domain.models.TempUnit
import com.neophron.main.domain.models.CurrentWeatherDisplay
import com.neophron.main.domain.models.DisplayWeather

data class SettingsUiState(
    val isInProgress: Boolean = false,
    val unit: TempUnit? = null
) {
    val isNotInProgress: Boolean get() = !isInProgress

}

