package org.flepper.currencyconvertor.di


expect class Context()

/**
 * Expect Class to be implemented on both platforms to get the application context
 * */
expect class KMMContext(context: Context){
    var application: Context
}