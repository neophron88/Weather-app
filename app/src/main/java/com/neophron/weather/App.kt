package com.neophron.weather

import android.app.Application
import com.neophron.contract.DependencyProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class App : Application(), DependencyProvider {

    private val applicationScope: CoroutineScope by lazy {
        CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }

    override val dependency: Any by lazy {

    }
}