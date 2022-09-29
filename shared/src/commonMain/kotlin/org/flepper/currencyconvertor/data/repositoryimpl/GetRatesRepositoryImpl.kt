package org.flepper.currencyconvertor.data.repositoryimpl

import co.touchlab.kermit.Logger
import com.rickclephas.kmp.nativecoroutines.NativeCoroutineScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.flepper.currencyconvertor.data.CurrencyRates
import org.flepper.currencyconvertor.data.THIRTY_MIN_INTERVAL
import org.flepper.currencyconvertor.data.apppreference.AppSettings
import org.flepper.currencyconvertor.data.model.OnResultObtained
import org.flepper.currencyconvertor.data.network.Api
import org.flepper.currencyconvertor.data.network.ApiResult
import org.flepper.currencyconvertor.data.network.makeRequestToApi
import org.flepper.currencyconvertor.data.repositories.GetRatesRepository
import org.flepper.currencyconvertor.utils.convertToCurrencyRates

class GetRatesRepositoryImpl() :
    GetRatesRepository,KoinComponent {

    @NativeCoroutineScope
    internal val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val appPreference: AppSettings by inject()
    private val api: Api by inject()



    private val _currencyRates = MutableStateFlow(
        OnResultObtained<CurrencyRates>(
            result = null,
            isLoaded = false,
            error = ""
        )
    )

    override fun currencyRates(): StateFlow<OnResultObtained<CurrencyRates>> = _currencyRates.asStateFlow()

    override  fun loadData() {

        val result = mutableListOf<ApiResult<String>>()

        val lastTimeStamp = appPreference.lasSuccessTimeStamp
        val difference =
            Clock.System.now().toEpochMilliseconds() - appPreference.lasSuccessTimeStamp

        Logger.e { "lasSuccessTimeStamp ${appPreference.lasSuccessTimeStamp} --- difference $difference --- THIRTY_MIN_INTERVAL $THIRTY_MIN_INTERVAL" }


        coroutineScope.launch {
           val getRates = async {
               if (lastTimeStamp == 0L|| difference > lastTimeStamp) {
                   return@async getLatestRates()
               } else {
                   ApiResult.Success(appPreference.localLatestJson)
               }
            }

            val getCurrencies = async {
                if (lastTimeStamp == 0L|| difference > lastTimeStamp) {
                    return@async getCurrencyNames()
                } else {
                    ApiResult.Success(appPreference.localCurrencyJson)
                }
            }

            awaitAll(getRates,getCurrencies).forEach { res ->
                result.add(res)
            }

            val first = result.first()
            val second = result[1]
            resolveCombineApiRequest(first,second,
                onSuccess = {rates,currencies ->
                _currencyRates.value = OnResultObtained(
                    convertToCurrencyRates(rates, currencies)!!,
                    true,
                    null
                )
                appPreference.updateLocalLatestJson(rates)
                appPreference.updateLocalCurrencyJson(currencies)
                appPreference.updateLasSuccessTimeStamp(Clock.System.now().toEpochMilliseconds())
            }, onInternetError = {error ->
                _currencyRates.value = OnResultObtained(
                    null,
                    true,
                    "error"
                )
            }, onGenericError = {error ->
                _currencyRates.value = OnResultObtained(
                    null,
                    true,
                    error
                )
            }, onHttpError = {error ->
                _currencyRates.value = OnResultObtained(
                    null,
                    true,
                    error
                )
            })
        }

    }


    override suspend fun getLatestRates(): ApiResult<String> {
        return makeRequestToApi {
            val lastTimeStamp = appPreference.lasSuccessTimeStamp
            val difference =
                Clock.System.now().toEpochMilliseconds() - appPreference.lasSuccessTimeStamp

            Logger.e { "lasSuccessTimeStamp ${appPreference.lasSuccessTimeStamp} --- difference $difference --- THIRTY_MIN_INTERVAL $THIRTY_MIN_INTERVAL" }
            if (lastTimeStamp == 0L || difference > lastTimeStamp){
                //difference > THIRTY_MIN_INTERVAL || lastTimeStamp == 0L) {
                val result = api.getLatestRates()
                result
            } else {
                appPreference.localLatestJson!!
            }

        }
    }

    override suspend fun getCurrencyNames(): ApiResult<String> {
        return makeRequestToApi {
            val lastTimeStamp = appPreference.lasSuccessTimeStamp
            val difference =
                Clock.System.now().toEpochMilliseconds() - appPreference.lasSuccessTimeStamp

            Logger.e { "lasSuccessTimeStamp ${appPreference.lasSuccessTimeStamp} --- difference $difference --- THIRTY_MIN_INTERVAL $THIRTY_MIN_INTERVAL" }
            if (lastTimeStamp == 0L|| difference > lastTimeStamp) {
                val result = api.getCurrencies()
                result
            } else {
                appPreference.localCurrencyJson!!
            }
        }
    }


}

/**
 * To Resolve to Requests or Combine
 * */
fun <First : Any, Second : Any> resolveCombineApiRequest(
    first: ApiResult<First>,
    second: ApiResult<Second>,
    onSuccess: (First, Second) -> Unit,
    onInternetError: (String) -> Unit,
    onHttpError: (String) -> Unit,
    onGenericError: (String) -> Unit
) {

    try {
        when {
            first is ApiResult.Success && second is ApiResult.Success -> {
                //Logger.e { "Success" }
                onSuccess(first.response, second.response)
            }
            first is ApiResult.NoInternet || second is ApiResult.NoInternet -> {
                onInternetError("No Internet Connection")
            }
            first is ApiResult.GenericError || second is ApiResult.GenericError -> {
                if (first is ApiResult.GenericError) {
                    onGenericError(first.error.message ?: "")
                    //Logger.e("Api Exception") { "${first.error} Some thing went Wrong" }
                }

                if (second is ApiResult.GenericError) {
                    onGenericError(second.error.message ?: "")
                    //Logger.e("Api Exception") { "${second.error} Some thing went Wrong" }
                }
            }
            first is ApiResult.HttpError || second is ApiResult.HttpError -> {
                if (first is ApiResult.HttpError) {
                    onHttpError(first.error.message ?: "")
                   // Logger.e("Api Exception") { "${first.error} Some thing went Wrong" }
                }

                if (second is ApiResult.HttpError) {
                    onHttpError(second.error.message ?: "")
                    //Logger.e("Api Exception") { "${second.error} Some thing went Wrong" }
                }
            }
        }

    }catch (e:Exception){
        onGenericError(e.message ?: "")
    }

}

fun shouldGetFromApi(difference: Long, interval: Long, lastTimeStamp: Long) = difference > interval