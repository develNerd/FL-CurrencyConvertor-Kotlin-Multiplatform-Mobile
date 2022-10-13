package org.flepper.currencyconvertor.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import org.flepper.currencyconvertor.data.apppreference.createMainDataStore
import org.flepper.currencyconvertor.data.apppreference.dataStoreFileName
import org.flepper.currencyconvertor.data.repositories.GetRatesRepository
import org.flepper.currencyconvertor.data.repositoryimpl.GetRatesRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.*

actual fun platformModule() : Module = module {
    single { createDataStore() }
    single<Settings> { NSUserDefaultsSettings(NSUserDefaults.standardUserDefaults) }
    single<GetRatesRepository> { GetRatesRepositoryImpl(get()) }
}

fun createDataStore(): DataStore<Preferences> = createMainDataStore(
    producePath = {
        val documentDirectory: NSURL? = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
        requireNotNull(documentDirectory).path + "/$dataStoreFileName"
    }
)
