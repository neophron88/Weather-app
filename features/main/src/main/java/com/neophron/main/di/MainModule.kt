package com.neophron.main.di

import com.neophron.domain.repositories.WeatherPreference
import com.neophron.main.domain.usecases.GetTempUnitUseCase
import com.neophron.main.domain.usecases.SetTempUnitUseCase
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun getTempUnitUseCase(preference: WeatherPreference) = GetTempUnitUseCase(preference)

    @Provides
    fun getSetTempUnitUseCase(preference: WeatherPreference) = SetTempUnitUseCase(preference)
}