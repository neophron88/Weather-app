package com.neophron.main.ui.weather_settings

import androidx.lifecycle.ViewModel
import com.neophron.main.domain.usecases.GetTempUnitUseCase
import com.neophron.main.domain.usecases.SetTempUnitUseCase

class SettingsViewModel(
    private val getTempUnitUseCase: GetTempUnitUseCase,
    private val setTempUnitUseCase: SetTempUnitUseCase
) : ViewModel() {
}