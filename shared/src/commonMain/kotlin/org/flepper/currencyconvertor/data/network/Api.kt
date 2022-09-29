package org.flepper.currencyconvertor.data.network

import co.touchlab.kermit.Logger
import io.ktor.client.statement.*
import org.flepper.currencyconvertor.BuildKonfig

class Api(private val apiClient: ApiClient) {


    var webKey:String = BuildKonfig.web_key

    suspend fun getLatestRates():String{
        Logger.e { "BuildConfig" + webKey }
        return apiClient.GET<String>(LATEST_JSON, listOf(Pair(APP_ID_QUERY_KEY,  webKey))).bodyAsText()
    }

    suspend fun getCurrencies():String{
        return apiClient.GET<String>(CURRENCY_JSON, listOf(Pair(APP_ID_QUERY_KEY,  webKey))).bodyAsText()
    }


}