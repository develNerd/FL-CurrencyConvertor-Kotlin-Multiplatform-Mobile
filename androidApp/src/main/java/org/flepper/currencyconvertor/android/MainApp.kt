package org.flepper.currencyconvertor.android

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.flepper.currencyconvertor.android.di.presentationModule
import org.flepper.currencyconvertor.di.initKoin
import org.flepper.currencyconvertor.di.sharedModule

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()

        //Initialise Koin for Dependency Injection
        initKoin {
            allowOverride(true)
            androidContext(this@MainApp)
            // if (BuildConfig.DEBUG)
            androidLogger(Level.ERROR)
            modules(appModules, sharedModule)
        }

    }


}

val appModules = presentationModule
