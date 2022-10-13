package org.flepper.currencyconvertor.android.di

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.flepper.currencyconvertor.android.MainActivityViewModel


val presentationModule = module {
    viewModel { MainActivityViewModel() }
}



