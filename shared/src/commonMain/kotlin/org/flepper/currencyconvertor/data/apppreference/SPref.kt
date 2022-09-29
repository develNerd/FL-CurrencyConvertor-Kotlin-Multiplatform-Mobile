package org.flepper.currencyconvertor.data.apppreference


expect fun SPref.getInt(key: String) : Int
expect fun SPref.setInt(key: String, value: Int)
expect fun SPref.putLong(key: String, value: Long)
expect fun SPref.getLong(key: String, default: Long): Long
expect fun SPref.putString(key: String, value: String)
expect fun SPref.getString(key: String) : String?
expect class SContext

/**
 * Expect Class to be implemented on both platforms to get the application context
 * */
expect class SPref(context: SContext){
    var application: SContext
}