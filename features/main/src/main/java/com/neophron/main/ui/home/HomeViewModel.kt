package com.neophron.main.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neophron.domain.models.Weather
import com.neophron.domain.repositories.WeatherRepository
import com.neophron.domain.results.WeatherResult
import com.neophron.domain.results.isPending
import com.neophron.main.domain.models.DisplayWeather
import com.neophron.main.ui.updateValue
import com.neophron88.library.singleusedata.MutableSingleUseData
import com.neophron88.library.singleusedata.SingleUseData
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class HomeViewModel(
    repository: WeatherRepository
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

    init {
        requestWeatherData()
    }


    private fun requestWeatherData() = viewModelScope.launch {
        listFlow.collect {
            _uiState.value = HomeUiState(list = it)
        }
    }


    private suspend fun transformFlow(
        current: WeatherResult<Weather>,
        days: WeatherResult<List<Weather>>
    ): List<DisplayWeather> {
        if (current.isPending() || days.isPending()) _uiState.updateValue {
            it.copy(isInProgress = true)
        }
        else if (current is WeatherResult.Error) handleError(current)
        else if (days is WeatherResult.Error) handleError(days)
        else if (days is WeatherResult.Success && current is WeatherResult.Success)
            _uiState.updateValue {
                it.copy(
                    isInProgress = false,
                    list = mapToDisplay(current.value, days.value)
                )
            }

    }

    private fun <T> handleError(result: WeatherResult.Error<T>) {
        _uiState.updateValue { it.copy(isInProgress = false) }

    }


}