package org.flepper.currencyconvertor.di

import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import org.koin.core.module.Module
import org.koin.dsl.module
import org.flepper.currencyconvertor.data.apppreference.KMMStorage
import org.flepper.currencyconvertor.data.apppreference.SPref
import org.flepper.currencyconvertor.data.repositories.GetRatesRepository
import org.flepper.currencyconvertor.data.repositoryimpl.GetRatesRepositoryImpl
import platform.Foundation.NSUserDefaults

actual fun platformModule() : Module = module {
    single { SPref(get()) }
    single { KMMStorage(get()) }
    single<Settings> { NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults) }
    single<GetRatesRepository> { GetRatesRepositoryImpl() }
}
