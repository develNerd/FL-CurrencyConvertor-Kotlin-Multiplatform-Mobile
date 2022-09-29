package org.flepper.currencyconvertor.data.apppreference

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

actual typealias SContext = Application

actual class SPref actual constructor(context: SContext){
    actual var application: SContext

    init {
        application = context
    }

    operator fun invoke():SContext{
        return application
    }
}

actual fun SPref.getInt(key: String ) : Int{
    val prefs: SharedPreferences = this().getSharedPreferences("MShared", MODE_PRIVATE)
    return prefs.getInt(key, -1)
}


actual fun SPref.setInt(key: String, value: Int) {
    val prefs: SharedPreferences = this().getSharedPreferences("MShared", MODE_PRIVATE)
    val editor = prefs.edit()
    editor.putInt(key,value)
    editor.apply()
}


actual fun SPref.putLong(
    key: String,
    value: Long
) {
    val prefs: SharedPreferences = this().getSharedPreferences("MShared", MODE_PRIVATE)
    val editor = prefs.edit()
    editor.putLong(key,value)
    editor.apply()
}

actual fun SPref.getLong(
    key: String,
    default: Long
): Long {
    val prefs: SharedPreferences = this().getSharedPreferences("MShared", MODE_PRIVATE)
    return prefs.getLong(key, 0L)
}

actual fun SPref.putString(
    key: String,
    value: String
) {
    val prefs: SharedPreferences = this().getSharedPreferences("MShared", MODE_PRIVATE)
    val editor = prefs.edit()
    editor.putString(key,value)
    editor.apply()
}

actual fun SPref.getString(key: String): String? {
    val prefs: SharedPreferences = this().getSharedPreferences("MShared", MODE_PRIVATE)
    return prefs.getString(key,"")
}