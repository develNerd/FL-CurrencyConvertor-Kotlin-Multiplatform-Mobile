package org.flepper.currencyconvertor.android.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.flepper.currencyconvertor.android.MainActivityViewModel
import org.flepper.currencyconvertor.data.apppreference.SContext
import org.flepper.currencyconvertor.data.apppreference.SPref

val presentationModule = module {
    viewModel { MainActivityViewModel() }
}

val appModule = module {
    single { SPref(androidContext() as SContext) }
}


