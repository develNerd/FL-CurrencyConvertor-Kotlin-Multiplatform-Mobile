package org.flepper.currencyconvertor.di

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.core.module.Module
import org.koin.dsl.module
import org.flepper.currencyconvertor.data.apppreference.KMMStorage
import org.flepper.currencyconvertor.data.repositories.GetRatesRepository
import org.flepper.currencyconvertor.data.repositoryimpl.GetRatesRepositoryImpl

actual fun platformModule() : Module = module {
    single { KMMStorage(get()) }
    single {  createObservableSettings(get()) }
    single<GetRatesRepository> { GetRatesRepositoryImpl() }
}


private fun createObservableSettings(context: Context): Settings {
    return SharedPreferencesSettings(context.getSharedPreferences("AppSettings", Context.MODE_PRIVATE))
}
