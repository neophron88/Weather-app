package com.neophron.weather.di

import android.app.Application
import com.neophron.data.di.DataModule
import com.neophron.database.base.di.DatabaseDaoModule
import com.neophron.main.di.HomeAssistedFactoryProvider
import com.neophron.main.di.MainModule
import com.neophron.main.di.SettingsAssistedFactoryProvider
import com.neophron.network.base.di.NetworkServicesModule
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkServicesModule::class,
        DatabaseDaoModule::class,
        DataModule::class,
        MainModule::class
    ]
)
interface AppComponent :
    HomeAssistedFactoryProvider,
    SettingsAssistedFactoryProvider {

    @Component.Factory
    interface AppComponentFactory {
        fun create(
            @BindsInstance application: Application,
            @BindsInstance longLiveScope: CoroutineScope
        ): AppComponent
    }

}