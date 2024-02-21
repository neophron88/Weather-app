package com.neophron.main.di

import com.neophron.main.ui.home.HomeViewModel
import com.neophron.main.ui.weather_settings.SettingsViewModel
import dagger.assisted.AssistedFactory


interface HomeAssistedFactoryProvider {
    fun getHomeFactory(): HomeAssistedFactory
}

@AssistedFactory
interface HomeAssistedFactory {
    fun create(): HomeViewModel
}


interface SettingsAssistedFactoryProvider {
    fun getSettingsFactory(): SettingsAssistedFactory
}

@AssistedFactory
interface SettingsAssistedFactory {
    fun create(): SettingsViewModel
}