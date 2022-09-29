package org.flepper.currencyconvertor.data.repositories

import kotlinx.coroutines.flow.StateFlow
import org.flepper.currencyconvertor.data.CurrencyRates
import org.flepper.currencyconvertor.data.model.OnResultObtained
import org.flepper.currencyconvertor.data.network.ApiResult

interface GetRatesRepository {
    suspend fun getLatestRates(): ApiResult<String>
    suspend fun getCurrencyNames():ApiResult<String>
    fun loadData()
    fun currencyRates():StateFlow<OnResultObtained<CurrencyRates>>
}