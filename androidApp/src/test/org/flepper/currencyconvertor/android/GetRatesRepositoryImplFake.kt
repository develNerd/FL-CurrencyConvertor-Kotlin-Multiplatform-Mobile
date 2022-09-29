package org.flepper.currencyconvertor.android

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.flepper.currencyconvertor.data.CurrencyRates
import org.flepper.currencyconvertor.data.model.OnResultObtained
import org.flepper.currencyconvertor.data.network.ApiResult
import org.flepper.currencyconvertor.data.network.makeRequestToApi
import org.flepper.currencyconvertor.data.repositories.GetRatesRepository


/**
 * Test repository for testing Sample Json
 * see [latestTestRatesJson] and [currenciesTestJson]
 * */
class GetRatesRepositoryImplFake(private val coroutineScope:CoroutineScope):GetRatesRepository {

    override suspend fun getLatestRates(): ApiResult<String> {
       return makeRequestToApi {
           latestTestRatesJson
       }
    }

    override suspend fun getCurrencyNames(): ApiResult<String> {
        return makeRequestToApi {
            currenciesTestJson
        }
    }

    override fun loadData() {

    }

    private val _currencyRates = MutableStateFlow(
        OnResultObtained<CurrencyRates>(
            result = null,
            isLoaded = false,
            error = ""
        )
    )

    override fun currencyRates(): StateFlow<OnResultObtained<CurrencyRates>> = _currencyRates.asStateFlow()
}



