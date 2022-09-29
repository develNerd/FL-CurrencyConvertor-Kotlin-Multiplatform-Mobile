package org.flepper.currencyconvertor.data.network

import io.ktor.client.plugins.*
import kotlinx.serialization.Serializable


@Serializable
data class ErrorMessage(
    val error:String,
    val status:Int,
    val message:String,
    val description:String
)


sealed class ApiResult<out T : Any> {

    data class Success<out T : Any>(val response: T) : ApiResult<T>()


    data class GenericError(val error: Exception) : ApiResult<Nothing>()

    data class HttpError(val error: ClientRequestException) : ApiResult<Nothing>()

    object InProgress : ApiResult<Nothing>()

    object NoInternet : ApiResult<Nothing>()

    // Override for quick logging
    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success [data=$response]"
            is HttpError -> "Http Error [httpCode=${error.message}]"
            is GenericError -> "Error [error=${error.message}]"
            is NoInternet -> "No Internet"
            is InProgress -> "In progress"
        }
    }


    fun toResult(): ApiResult<*> {
        return when (this) {
            is HttpError -> HttpError(error)
            is GenericError -> GenericError(error)
            is NoInternet -> NoInternet
            is InProgress -> InProgress
            else -> InProgress
        }
    }
}
