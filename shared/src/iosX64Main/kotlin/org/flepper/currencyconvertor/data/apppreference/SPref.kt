package org.flepper.currencyconvertor.data.apppreference

import platform.Foundation.NSUserDefaults
import platform.darwin.NSObject

actual fun SPref.getInt(key: String) : Int {
    return NSUserDefaults.standardUserDefaults.integerForKey(key).toInt()
}

actual fun SPref.setInt(key: String, value : Int){
    NSUserDefaults.standardUserDefaults.setInteger(value.toLong(),key)
}

actual typealias SContext = NSObject

/**
 * Expect Class to be implemented on both platforms to get the application context
 * */
actual class SPref actual constructor(context: SContext) {
    actual var application:SContext = context
}

actual fun SPref.putLong(
    key: String,
    value: Long
) {
    NSUserDefaults.standardUserDefaults.setInteger(value,key)
}

actual fun SPref.getLong(
    key: String,
    default: Long
): Long {
    return NSUserDefaults.standardUserDefaults.integerForKey(key)
}

actual fun SPref.putString(
    key: String,
    value: String
) {
    NSUserDefaults.standardUserDefaults.setObject(value, key)
}

actual fun SPref.getString(key: String): String? {
    return NSUserDefaults.standardUserDefaults.stringForKey(key)
}