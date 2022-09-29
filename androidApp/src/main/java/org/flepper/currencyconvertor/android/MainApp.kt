package org.flepper.currencyconvertor.android

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.flepper.currencyconvertor.android.di.appModule
import org.flepper.currencyconvertor.android.di.presentationModule
import org.flepper.currencyconvertor.di.initKoin
import org.flepper.currencyconvertor.di.netWorkingModule

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()

        //Initialise Koin for Dependency Injection
        initKoin {
            allowOverride(true)
            androidContext(this@MainApp)
            // if (BuildConfig.DEBUG)
            androidLogger(Level.ERROR)
            modules(appModule,appModules, netWorkingModule)
        }

    }


}

val appModules = presentationModule
