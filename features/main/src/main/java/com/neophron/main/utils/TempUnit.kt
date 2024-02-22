package com.neophron.main.utils

import androidx.annotation.StringRes
import com.neophron.domain.models.TempUnit

@StringRes
fun TempUnit.toStringPlaceHolderRes(): Int = when (this) {
    TempUnit.Fahrenheit -> com.neophron.ui.R.string.fahrenheit_placeholder
    TempUnit.Celsius -> com.neophron.ui.R.string.celsius_placeholder
    TempUnit.Kelvin -> com.neophron.ui.R.string.kelvin_placeholder
}

@StringRes
fun TempUnit.parseToStringRes(): Int = when (this) {
    TempUnit.Fahrenheit -> com.neophron.ui.R.string.fahrenheit
    TempUnit.Celsius -> com.neophron.ui.R.string.celsius
    TempUnit.Kelvin -> com.neophron.ui.R.string.kelvin
}

