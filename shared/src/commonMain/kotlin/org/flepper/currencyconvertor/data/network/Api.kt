package org.flepper.currencyconvertor.data.network

import io.ktor.client.statement.*

class Api(private val apiClient: ApiClient) {


    //TODO(Comment out if you need to use BuildKonfig)
    //var webKey:String = BuildKonfig.web_key

    suspend fun getLatestRates():String{
        return apiClient.GET<String>(LATEST_JSON, listOf(Pair(APP_ID_QUERY_KEY,  OPEN_RATES_API_KEY))).bodyAsText()
    }

    suspend fun getCurrencies():String{
        return apiClient.GET<String>(CURRENCY_JSON, listOf(Pair(APP_ID_QUERY_KEY,  OPEN_RATES_API_KEY))).bodyAsText()
    }


}