package org.flepper.currencyconvertor.data.apppreference


class KMMStorage(val context: SPref) {

    private val LAST_SUCCESS_ATTEMPT = "LAST_SUCCESS_ATTEMPT"
    private val LOCAL_LATEST_JSON = "LOCAL_LATEST_JSON"
    private val LOCAL_CURRENCY_JSON = "LOCAL_CURRENCY_JSON"

    var lasSuccessTimeStamp: Long
        get() = context.getLong(LAST_SUCCESS_ATTEMPT, 0L)
        set(value) = context.putLong(LAST_SUCCESS_ATTEMPT, value)


    var localLatestJson: String?
        get() = context.getString(LOCAL_LATEST_JSON)
        set(value) = context.putString(LOCAL_LATEST_JSON, value ?: "")

    var localCurrencyJson: String?
        get() = context.getString(LOCAL_CURRENCY_JSON)
        set(value) = context.putString(LOCAL_CURRENCY_JSON, value ?: "")

    fun getInt(key: String): Int {
        return context.getInt(key)
    }

    fun putInt(key: String, value: Int) {
        context.setInt(key,value)
    }

    fun putString(key: String,value: String){
        context.putString(key, value)
    }

    fun getString(key: String):String?{
       return context.getString(key)
    }

    fun putLong(key: String,value:Long){
        context.putLong(key, value)
    }

    fun getLong(key: String):Long{
       return context.getLong(key,0)
    }

}