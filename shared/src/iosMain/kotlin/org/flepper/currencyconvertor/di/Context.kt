package org.flepper.currencyconvertor.di

import platform.darwin.NSObject

actual typealias Context = NSObject

/**
 * Expect Class to be implemented on both platforms to get the application context
 * */
actual class KMMContext actual constructor(context: Context) {
    actual var application: Context = context
}
