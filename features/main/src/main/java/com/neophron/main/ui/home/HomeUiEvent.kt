package com.neophron.main.ui.home

import com.neophron.domain.errors.ErrorType

sealed class HomeUiEvent {
    class Error(val type: ErrorType) : HomeUiEvent()
}

