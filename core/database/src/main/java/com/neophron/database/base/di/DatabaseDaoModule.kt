package com.neophron.database.base.di

import com.neophron.database.base.WeatherDatabase
import com.neophron.database.current_weather.CurrentWeatherDao
import com.neophron.database.several_days_weather.DaysWeatherDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DatabaseModule::class])
class DatabaseDaoModule {

    @Provides
    @Singleton
    fun provideCurrentWeatherDao(database: WeatherDatabase): CurrentWeatherDao =
        database.getCurrentWeatherDao()


    @Provides
    @Singleton
    fun provideDaysWeatherDao(database: WeatherDatabase): DaysWeatherDao =
        database.getDaysWeatherDao()
}