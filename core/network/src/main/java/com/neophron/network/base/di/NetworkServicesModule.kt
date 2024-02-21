package com.neophron.network.base.di

import com.neophron.network.several_days_weather.SeveralDaysWeatherService
import com.neophron.network.current_weather.CurrentWeatherService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton


@Module(includes = [NetworkModule::class])
class NetworkServicesModule {

    @Provides
    @Singleton
    fun provideSeveralDaysWeatherService(retrofit: Retrofit): SeveralDaysWeatherService {
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun provideTodayWeatherService(retrofit: Retrofit): CurrentWeatherService {
        return retrofit.create()
    }

}