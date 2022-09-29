package org.flepper.currencyconvertor.repositoryImplTests

import org.flepper.currencyconvertor.currenciesTestJson
import org.flepper.currencyconvertor.data.network.ApiResult
import org.flepper.currencyconvertor.data.repositoryimpl.resolveCombineApiRequest
import org.flepper.currencyconvertor.latestTestRatesJson
import kotlin.test.Test
import kotlin.test.assertTrue


class GetRatesRepositoryTest {

    @Test
    fun testSuccessResolveCombineResult(){
        val rates = ApiResult.Success(latestTestRatesJson)
        val currencies = ApiResult.Success(currenciesTestJson)

        resolveCombineApiRequest(rates,currencies, onSuccess = { rate,currency ->
            assertTrue(rate.isNotEmpty())
            assertTrue(currency.isNotEmpty())
        }, onHttpError = {
            assertTrue(it.isEmpty())
        }, onGenericError = {
            assertTrue(it.isEmpty())
        }, onInternetError = {
            assertTrue(it.isEmpty())
        })
    }

    @Test
    fun testInternetErrorResolveCombineResult(){
        val rates = ApiResult.NoInternet
        val currencies = ApiResult.Success("")

        resolveCombineApiRequest(rates,currencies, onSuccess = { rate,currency ->
            //
        }, onHttpError = {
            assertTrue(it.isEmpty())
        }, onGenericError = {
            assertTrue(it.isEmpty())
        }, onInternetError = {
            assertTrue(it.isNotEmpty())
        })
    }

    @Test
    fun testGenericErrorResolveCombineResult(){

        val rates = ApiResult.GenericError(Exception("Genric"))
        val currencies = ApiResult.GenericError(Exception("Genric"))

        resolveCombineApiRequest(rates,currencies, onSuccess = { rate,currency ->
            //
        }, onHttpError = {
            assertTrue(it.isNotEmpty())
        }, onGenericError = {
            assertTrue(it.isNotEmpty())
        }, onInternetError = {
            assertTrue(it.isEmpty())
        })
    }

}