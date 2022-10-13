package org.flepper.currencyconvertor.data.repositoryimpl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import co.touchlab.kermit.Logger
import com.rickclephas.kmp.nativecoroutines.NativeCoroutineScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.flepper.currencyconvertor.data.CurrencyRates
import org.flepper.currencyconvertor.data.THIRTY_MIN_INTERVAL
import org.flepper.currencyconvertor.data.apppreference.AppDataStore
import org.flepper.currencyconvertor.data.model.OnResultObtained
import org.flepper.currencyconvertor.data.network.Api
import org.flepper.currencyconvertor.data.network.ApiResult
import org.flepper.currencyconvertor.data.network.makeRequestToApi
import org.flepper.currencyconvertor.data.repositories.GetRatesRepository
import org.flepper.currencyconvertor.utils.convertToCurrencyRates

class GetRatesRepositoryImpl(private val dataStore: AppDataStore) :
    GetRatesRepository, KoinComponent {

    @NativeCoroutineScope
    internal val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val api: Api by inject()


    private val _currencyRates = MutableStateFlow(
        OnResultObtained<CurrencyRates>(
            result = null,
            isLoaded = false,
            error = ""
        )
    )

    override fun currencyRates(): StateFlow<OnResultObtained<CurrencyRates>> =
        _currencyRates.asStateFlow()

    override fun loadData() {

        val result = mutableListOf<ApiResult<String>>()

        coroutineScope.launch {


            val lastTimeStamp = dataStore.lasSuccessTimeStamp.first()
            val lasSuccessTimeStamp = dataStore.lasSuccessTimeStamp.first()

            val difference =
                Clock.System.now().toEpochMilliseconds() - lasSuccessTimeStamp

            Logger.e { "lasSuccessTimeStamp $lasSuccessTimeStamp --- difference $difference --- THIRTY_MIN_INTERVAL $THIRTY_MIN_INTERVAL" }


            val getRates = async {
                if (lastTimeStamp == 0L || difference > lastTimeStamp) {
                    return@async getLatestRates()
                } else {
                    ApiResult.Success(dataStore.localLatestJson.first())
                }
            }

            val getCurrencies = async {
                if (lastTimeStamp == 0L || difference > lastTimeStamp) {
                    return@async getCurrencyNames()
                } else {
                    ApiResult.Success(dataStore.localCurrencyJson.first())
                }
            }

            awaitAll(getRates, getCurrencies).forEach { res ->
                result.add(res)
            }

            val first = result.first()
            val second = result[1]
            resolveCombineApiRequest(first, second,
                onSuccess = { rates, currencies ->
                    _currencyRates.value = OnResultObtained(
                        convertToCurrencyRates(rates, currencies)!!,
                        true,
                        null
                    )
                    dataStore.updateLocalLatestJson(rates)
                    dataStore.updateLocalCurrencyJson(currencies)
                    dataStore.updateLasSuccessTimeStamp(Clock.System.now().toEpochMilliseconds())
                }, onInternetError = { error ->
                    _currencyRates.value = OnResultObtained(
                        null,
                        true,
                        "error"
                    )
                }, onGenericError = { error ->
                    _currencyRates.value = OnResultObtained(
                        null,
                        true,
                        error
                    )
                }, onHttpError = { error ->
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
            api.getLatestRates()
        }
    }

    override suspend fun getCurrencyNames(): ApiResult<String> {
        return makeRequestToApi {
            api.getCurrencies()
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
                onSuccess(first.response, second.response)
            }
            first is ApiResult.NoInternet || second is ApiResult.NoInternet -> {
                onInternetError("No Internet Connection")
            }
            first is ApiResult.GenericError || second is ApiResult.GenericError -> {
                if (first is ApiResult.GenericError) {
                    onGenericError(first.error.message ?: "")
                }

                if (second is ApiResult.GenericError) {
                    onGenericError(second.error.message ?: "")
                }
            }
            first is ApiResult.HttpError || second is ApiResult.HttpError -> {
                if (first is ApiResult.HttpError) {
                    onHttpError(first.error.message)
                }

                if (second is ApiResult.HttpError) {
                    onHttpError(second.error.message)
                }
            }
        }

    } catch (e: Exception) {
        onGenericError(e.message ?: "")
    }

}

fun shouldGetFromApi(difference: Long, interval: Long, lastTimeStamp: Long) = difference > interval