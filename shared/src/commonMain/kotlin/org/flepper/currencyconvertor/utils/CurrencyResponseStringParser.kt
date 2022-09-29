package org.flepper.currencyconvertor.utils

import co.touchlab.kermit.Logger
import org.flepper.currencyconvertor.data.Currency
import org.flepper.currencyconvertor.data.CurrencyRates


object CurrencyResponseStringParser {
    /**
     *
     * Convert response string to [CurrencyRates] object
     *
     * */
    fun parseAsCurrencyRates(text: String): CurrencyRates? {
        return try {
            val currencies = mutableListOf<Currency>()
            var populate = false
            var base = ""
            val listOfRates = mutableListOf<String>()

            //Logger.e { "Rates  ${listOfRates.toTypedArray().contentToString()}" }
            val regex = Regex("[a-zA-Z\\d\\s]")

            // split individual fields using the ","
            // e.g "base":"usd" , "rate" : {....} -> at this[0] we have "base":"usd"
            text.split(",").forEachIndexed { index, s ->
                val txt = s
                // Get the base from this "base":"AnyBaseHere"
                if (s.contains("\"base\"")) {
                    base =
                        s.split(":").last().trim().filter { char -> regex.matches(char.toString()) }
                }
                //Iterate till we get to rates we begin adding to the mutable list
                if (s.contains("\"rates\"")) {
                    populate = true
                }
                if (populate) {
                    listOfRates.add(s)
                }
            }
            // remove redundant "}" at the end of the response string
           // listOfRates.removeLast()


            // we now have something like this {"rates" : {.......... i.e remember we removed "}"
            listOfRates.forEachIndexed { index, s ->
                if (index == 0) {
                    val rString = s.split("{").last().trim()
                    val code =
                        rString.split(":").first().filter { char -> regex.matches(char.toString()) }
                    val usdRate = rString.split(":")[1].toDouble()
                    currencies.add(Currency(code, usdRate, "", 0.0))
                } else {
                    val code =
                        s.split(":").first().filter { char -> regex.matches(char.toString()) }
                            .trim()
                    val usdRate = s.split(":")[1].replace("}","").toDouble()
                    currencies.add(Currency(code, usdRate, "", 0.0))
                }
            }
            CurrencyRates(base, currencies)
        } catch (e: Exception) {
            null
        }

    }

    /**
     * Let's return a key value pair
     * */
    fun parseToCurrencyMapName(text: String): Map<String, String> {

        val mapOfCodeAndName = mutableMapOf<String, String>()
        val validNames = text.apply {
            replace("{", "")
            replace("}", "")
        }

        validNames.split(",").forEach { txt ->
            // should give "AED":"United Arab Emirates Dirham"
            val regex = Regex("[a-zA-Z\\d\\s]")

            // Get code to be used as key
            // Get name to be used as value
            val isoCode =
                txt.split(":").first().filter { char -> regex.matches(char.toString()) }.trim()
            val name = txt.split(":")[1].filter { char -> regex.matches(char.toString()) }.trim()

            mapOfCodeAndName[isoCode] = name
        }


        return mapOfCodeAndName
    }
}
/**
 *
 * Convert response string to [CurrencyRates] object
 *
 * */
fun convertToCurrencyRates(
    rates: String,
    currencies: String
): CurrencyRates? {
    return try {
        val mapOfCodeAndName = CurrencyResponseStringParser.parseToCurrencyMapName(currencies)
        val response = CurrencyResponseStringParser.parseAsCurrencyRates(rates)
            .apply { this?.rates?.map { it.name = mapOfCodeAndName[it.code] ?: "" } }

        response!!
    }catch(e:Exception){
        Logger.e{ "Parse Error $e" }
        null
    }
}
/**
 * Let's return a key value pair
 * */
fun String.parseToCurrencyMapName():Map<String,String>{

    val mapOfCodeAndName = mutableMapOf<String,String>()
    val validNames = this.apply {
        replace("{","")
        replace("}","")
    }

    validNames.split(",").forEach {txt ->
        // should give "AED":"United Arab Emirates Dirham"
        val regex = Regex("[a-zA-Z\\d\\s]")

        // Get code to be used as key
        // Get name to be used as value
        val isoCode = txt.split(":").first().filter {char -> regex.matches(char.toString()) }.trim()
        val name = txt.split(":")[1].filter {char -> regex.matches(char.toString()) }.trim()

        mapOfCodeAndName[isoCode] = name
    }


    return mapOfCodeAndName
}

