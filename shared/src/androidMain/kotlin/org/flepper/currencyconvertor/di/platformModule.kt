package org.flepper.currencyconvertor.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.flepper.currencyconvertor.data.apppreference.AppDataStore
import org.flepper.currencyconvertor.data.apppreference.createMainDataStore
import org.flepper.currencyconvertor.data.apppreference.dataStoreFileName
import org.flepper.currencyconvertor.data.repositories.GetRatesRepository
import org.flepper.currencyconvertor.data.repositoryimpl.GetRatesRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule() : Module = module {
    single {  createObservableSettings(get()) }
    single { createDataStore(get()) }
    single<GetRatesRepository> { GetRatesRepositoryImpl(get()) }
}


private fun createObservableSettings(context: Context): Settings {
    return SharedPreferencesSettings(context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE))
}


fun createDataStore(context: Context): DataStore<Preferences> = createMainDataStore(
    producePath = { context.filesDir.resolve(dataStoreFileName).absolutePath }
)
