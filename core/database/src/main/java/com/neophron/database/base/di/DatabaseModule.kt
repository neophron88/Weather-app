package com.neophron.database.base.di

import android.app.Application
import com.neophron.database.base.WeatherDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class DatabaseModule {

    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): WeatherDatabase =
        WeatherDatabase.getDatabase(application)

}