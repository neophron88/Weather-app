package com.neophron.main.ui.weather_settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neophron.domain.models.TempUnit
import com.neophron.main.domain.usecases.GetTempUnitUseCase
import com.neophron.main.domain.usecases.SetTempUnitUseCase
import com.neophron.main.utils.updateValue
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SettingsViewModel @AssistedInject constructor(
    private val getTempUnitUseCase: GetTempUnitUseCase,
    private val setTempUnitUseCase: SetTempUnitUseCase
) : ViewModel() {

    private val _uiState = MutableLiveData<SettingsUiState>()
    val uiState: LiveData<SettingsUiState> get() = _uiState

    private var checkedUnitJob: Job? = null

    init {
        getTempUnit()
    }

    private fun getTempUnit() = viewModelScope.launch {
        _uiState.value = SettingsUiState(isInProgress = true)
        val unit = getTempUnitUseCase()
        _uiState.updateValue { it.copy(isInProgress = false, unit = unit) }
    }

    fun setTempUnit(unit: TempUnit) {
        checkedUnitJob?.cancel()
        checkedUnitJob = viewModelScope.launch {
            delay(500)
            setTempUnitUseCase(unit)
        }
    }

}