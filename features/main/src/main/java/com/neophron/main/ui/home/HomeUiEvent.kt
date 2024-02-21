package com.neophron.main.ui.home

import androidx.annotation.StringRes

sealed class HomeUiEvent {
    class ToastMessage(@StringRes val messageRes: Int) : HomeUiEvent()
}

