package org.flepper.currencyconvertor.di

actual class Context actual constructor()

/**
 * Expect Class to be implemented on both platforms to get the application context
 * */
actual class KMMContext actual constructor(context: Context) {
    actual var application: Context
        get() = TODO("Not yet implemented")
        set(value) {}
}