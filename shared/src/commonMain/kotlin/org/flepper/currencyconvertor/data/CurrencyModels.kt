package org.flepper.currencyconvertor.data


data class CurrencyRates(
    val base: String = "",
    var rates: List<Currency>,
)


data class Currency(val code: String, var usdRate: Double,var name:String,var conversion:Double = 0.0){
    fun setCurrencyName(value:String){
        name = value
    }
}

