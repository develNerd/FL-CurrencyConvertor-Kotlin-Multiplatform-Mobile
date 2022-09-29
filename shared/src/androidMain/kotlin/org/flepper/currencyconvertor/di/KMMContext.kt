package org.flepper.currencyconvertor.di

import android.app.Application

actual typealias Context = Application

actual class KMMContext actual constructor(context: Context){
    actual var application: Context

    init {
        application = context
    }
}