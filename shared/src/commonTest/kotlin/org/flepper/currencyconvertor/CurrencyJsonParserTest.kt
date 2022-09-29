package org.flepper.currencyconvertor

import org.flepper.currencyconvertor.utils.CurrencyResponseStringParser
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CurrencyJsonParserTest {



    /**
     * For parsing currency Json to map
     * Should return a key value pair of Code as (key) and Name as (Value)
     * Sample like @see [currenciesTestJson]
     * */
    @Test
    fun testParseCurrenciesJson(){
        val mapOfCodeAndName = CurrencyResponseStringParser.parseToCurrencyMapName(currenciesTestJson)
        //size => 8 i.e number of currencies
        assertEquals(mapOfCodeAndName.size,8)
        assertEquals(mapOfCodeAndName.keys.first(),"AED")
        assertEquals(mapOfCodeAndName.values.first(),"United Arab Emirates Dirham")
        assertEquals(mapOfCodeAndName["AUD"],"Australian Dollar")
        assertTrue(mapOfCodeAndName["Argentine Peso"] == null)
        assertTrue(mapOfCodeAndName["ANG"] != null)
    }



    /**
     * Check for successful parse of rate to
     * [CurrenRates]Object
     * @see [latestTestRatesJson]
     * */
    @Test
    fun testParseRatesToObject(){
        val parsedObject = CurrencyResponseStringParser.parseAsCurrencyRates(latestTestRatesJson) // CurrencyRates
        assertTrue(parsedObject != null)
        assertTrue(parsedObject.rates.isNotEmpty())
        assertTrue(parsedObject.base == "USD")
        assertEquals(parsedObject.rates.size,5)
        assertTrue(parsedObject.rates.first().usdRate == 3.67301)
    }


}