package com.tech.building

import android.app.Application
import com.tech.building.domain.di.domainModule
import com.tech.building.features.di.presentationModule
import com.tech.building.gateway.di.gatewayModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApp)

            modules(
                presentationModule,
                domainModule,
                gatewayModule
            )
        }
    }
}