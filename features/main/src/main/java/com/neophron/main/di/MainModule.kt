package com.neophron.main.di

import android.app.Application
import com.neophron.domain.repositories.WeatherPreference
import com.neophron.domain.repositories.WeatherRepository
import com.neophron.main.domain.usecases.GetTempUnitUseCase
import com.neophron.main.domain.usecases.SetTempUnitUseCase
import com.neophron.main.ui.MapperDisplay
import com.neophron.main.ui.MapperDisplayImpl
import com.neophron.main.utils.StringResProvider
import com.neophron.main.utils.StringResProviderImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class MainModule {

    @Provides
    fun getTempUnitUseCase(preference: WeatherPreference) = GetTempUnitUseCase(preference)

    @Provides
    fun getSetTempUnitUseCase(repository: WeatherRepository) = SetTempUnitUseCase(repository)

    @Provides
    fun getStringProvider(application: Application): StringResProvider =
        StringResProviderImpl(application)

    @Provides
    fun getMapperDisplay(
        provider: StringResProvider,
        getTempUnitUseCase: GetTempUnitUseCase
    ): MapperDisplay =
        MapperDisplayImpl(provider, getTempUnitUseCase, Dispatchers.IO)
}