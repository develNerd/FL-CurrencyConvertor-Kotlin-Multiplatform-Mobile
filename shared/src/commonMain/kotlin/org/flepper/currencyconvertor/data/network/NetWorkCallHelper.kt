package org.flepper.currencyconvertor.data.network



import co.touchlab.kermit.Logger
import io.ktor.client.plugins.*
import io.ktor.utils.io.errors.*

suspend fun <T : Any> makeRequestToApi(
    call: suspend () -> T,
): ApiResult<T> {
    return try {
        ApiResult.Success(call())
    } catch (throwable: Exception) {
        throwable.printStackTrace()
        when (throwable) {
            is ServerResponseException -> {
                ApiResult.GenericError(throwable)
            }
            is ClientRequestException ->{
                ApiResult.GenericError(throwable)
            }
            is IOException -> {
                Logger.v("Error : $throwable")
                 ApiResult.NoInternet
            }
            else -> ApiResult.GenericError(throwable)
        }
    }
}