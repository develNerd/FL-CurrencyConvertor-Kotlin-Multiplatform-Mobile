package org.flepper.currencyconvertor

import org.koin.core.context.startKoin
import org.flepper.currencyconvertor.di.netWorkingModule

class HelperKt {
    fun initKoin(){
        startKoin {
            modules(netWorkingModule)
        }
    }
}