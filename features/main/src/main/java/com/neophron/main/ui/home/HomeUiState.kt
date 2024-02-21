package com.neophron.main.ui.home

import com.neophron.main.domain.models.DisplayWeather

data class HomeUiState(
    val isInProgress: Boolean = false,
    val list: List<DisplayWeather> = listOf()
) {
    val isNotInProgress: Boolean get() = !isInProgress
    val isEmpty: Boolean get() = list.isEmpty()

}

