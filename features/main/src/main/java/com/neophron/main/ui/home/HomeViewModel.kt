package com.neophron.main.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neophron.domain.errors.ErrorType
import com.neophron.domain.models.Weather
import com.neophron.domain.models.WeatherSettings
import com.neophron.domain.repositories.WeatherRepository
import com.neophron.domain.results.WeatherResult
import com.neophron.domain.results.isPending
import com.neophron.main.ui.MapperDisplay
import com.neophron.main.utils.updateValue
import com.neophron88.library.singleusedata.MutableSingleUseData
import com.neophron88.library.singleusedata.SingleUseData
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class HomeViewModel @AssistedInject constructor(
    private val mapperDisplay: MapperDisplay,
    private val repository: WeatherRepository,
) : ViewModel() {


    private val _uiState = MutableLiveData(HomeUiState(true))
    val uiState: LiveData<HomeUiState> get() = _uiState

    private val _uiEvent: MutableSingleUseData<HomeUiEvent> = MutableSingleUseData()
    val uiEvent: SingleUseData<HomeUiEvent> get() = _uiEvent

    private val listFlow = combine(
        repository.getCurrentWeather(),
        repository.getDaysWeather(),
        ::transformFlow
    )

    private var allDataJob: Job? = null

    init {
        requestWeatherData()
    }


    fun requestWeatherData() {
        allDataJob = viewModelScope.launch { listFlow.collect() }
    }

    private suspend fun transformFlow(
        current: WeatherResult<Weather>,
        days: WeatherResult<List<Weather>>
    ) {
        if (current.isPending() || days.isPending())
            _uiState.updateValue { it.copy(isInProgress = true) }
        else if (current is WeatherResult.Error) handleError(current)
        else if (days is WeatherResult.Success && current is WeatherResult.Success)
            _uiState.updateValue {
                it.copy(
                    isInProgress = false,
                    current = mapperDisplay.mapToCurrentWeatherDisplay(current.value),
                    list = mapperDisplay.mapToDayAndHourDisplay(current.value, days.value)
                )
            }

    }

    fun setCity(name: String) = viewModelScope.launch {
        _uiState.updateValue { it.copy(isInProgress = true) }
        val result = repository.changeWeatherSettings(WeatherSettings.CityParam(name.trim()))
        if (result is WeatherResult.Error) handleError(result)

    }

    private fun <T> handleError(result: WeatherResult.Error<T>) {
        _uiState.updateValue { it.copy(isInProgress = false) }
        _uiEvent.updateValue { HomeUiEvent.Error(result.type) }
    }
}