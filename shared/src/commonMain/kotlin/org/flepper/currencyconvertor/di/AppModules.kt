package org.flepper.currencyconvertor.di


import kotlinx.coroutines.MainScope
import org.flepper.currencyconvertor.data.apppreference.AppDataStore
import org.koin.dsl.module
import org.flepper.currencyconvertor.data.apppreference.AppSettings
import org.flepper.currencyconvertor.data.network.Api
import org.flepper.currencyconvertor.data.network.ApiClient

val sharedModule = module {
    single { MainScope() }
    single{ApiClient() }
    single { Api(get()) }
    single { AppDataStore(get()) }
    single { AppSettings(get()) }
    single { RepositoryHelper() }
}





