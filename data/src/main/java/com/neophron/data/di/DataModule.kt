package com.neophron.data.di

import android.app.Application
import com.neophron.data.OfflineFirstWeatherRepositoryImpl
import com.neophron.data.WeatherPreferencesImpl
import com.neophron.database.current_weather.CurrentWeatherDao
import com.neophron.database.several_days_weather.DaysWeatherDao
import com.neophron.domain.repositories.WeatherPreference
import com.neophron.domain.repositories.WeatherRepository
import com.neophron.network.current_weather.CurrentWeatherService
import com.neophron.network.several_days_weather.SeveralDaysWeatherService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataModule {


    @Provides
    @Singleton
    fun providePreference(application: Application): WeatherPreference =
        WeatherPreferencesImpl(application)

    @Provides
    @Singleton
    fun provideWeatherRepository(
        currentWeatherService: CurrentWeatherService,
        currentWeatherDao: CurrentWeatherDao,
        daysWeatherService: SeveralDaysWeatherService,
        daysWeatherDao: DaysWeatherDao,
        preference: WeatherPreference
    ): WeatherRepository = OfflineFirstWeatherRepositoryImpl(
        currentWeatherService, currentWeatherDao, daysWeatherService, daysWeatherDao, preference
    )

}