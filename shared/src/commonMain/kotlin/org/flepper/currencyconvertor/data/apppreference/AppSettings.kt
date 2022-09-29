package org.flepper.currencyconvertor.data.apppreference

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.Settings

@OptIn(ExperimentalSettingsApi::class)
class AppSettings(val settings: Settings) {



    var lasSuccessTimeStamp = settings.getLong(LAST_SUCCESS_ATTEMPT,0L)
    fun updateLasSuccessTimeStamp(value:Long) = settings.putLong(LAST_SUCCESS_ATTEMPT,value)


    var localLatestJson: String = settings.getString(LOCAL_LATEST_JSON,"")
     fun updateLocalLatestJson(value: String)  = settings.putString(LOCAL_LATEST_JSON, value ?: "")

    var localCurrencyJson: String  = settings.getString(LOCAL_CURRENCY_JSON,"")
    fun updateLocalCurrencyJson(value: String) = settings.putString(LOCAL_CURRENCY_JSON, value ?: "")


    companion object {
        const val LAST_SUCCESS_ATTEMPT = "LAST_SUCCESS_ATTEMPT"
        const val LOCAL_LATEST_JSON = "LOCAL_LATEST_JSON"
        const val LOCAL_CURRENCY_JSON = "LOCAL_CURRENCY_JSON"
    }
}