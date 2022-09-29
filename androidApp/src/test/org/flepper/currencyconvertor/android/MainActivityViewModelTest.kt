package org.flepper.currencyconvertor.android

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.flepper.currencyconvertor.data.network.ApiResult
import org.flepper.currencyconvertor.data.repositories.GetRatesRepository
import org.flepper.currencyconvertor.utils.convertToCurrencyRates
import kotlin.math.roundToInt
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
class MainActivityViewModelTest: KoinTest {



    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val scope = TestScope()

    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var getRatesRepository: GetRatesRepository


    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup(){

        getRatesRepository = GetRatesRepositoryImplFake(scope)
        mainActivityViewModel = MainActivityViewModel()

        runBlocking {
            val combinedResult = mutableListOf<ApiResult<String>>()

            getRatesRepository.getLatestRates().apply {
                combinedResult.add(this)
            }


            getRatesRepository.getCurrencyNames().apply {
                combinedResult.add(this)
            }



            assertTrue(combinedResult.isNotEmpty())
            val first = combinedResult.first()
            val second = combinedResult[1]
            assertTrue(first is ApiResult.Success)
            assertTrue(second is ApiResult.Success)

            if(first is ApiResult.Success && second is ApiResult.Success){

                /**
                 * should expect this @see [latestTestRatesJson] & [currenciesTestJson]] to change or update test Json
                 * CurrencyRates(base=USD,
                 * rates=[Currency(code=AED, usdRate=3.67301, name=United Arab Emirates Dirham, conversion=0.0),
                 * Currency(code=AFN, usdRate=88.932342, name=Afghan Afghani, conversion=0.0),
                 * Currency(code=ALL, usdRate=116.861551, name=Albanian Lek, conversion=0.0),
                 * Currency(code=AMD, usdRate=416.662312, name=Armenian Dram, conversion=0.0),
                 * Currency(code=ANG, usdRate=1.801994, name=Netherlands Antillean Guilder, conversion=0.0)])
                 */

                val currencyRate = convertToCurrencyRates(first.response,second.response)
                kotlin.test.assertTrue(currencyRate?.rates!!.isNotEmpty())
                mainActivityViewModel.parseRateResults(currencyRate)
            }





        }


    }


    @Test
    fun getCurrencyRatesTest() = runBlocking {

        val combinedResult = mutableListOf<ApiResult<String>>()

        getRatesRepository.getLatestRates().apply {
            combinedResult.add(this)
        }


        getRatesRepository.getCurrencyNames().apply {
            combinedResult.add(this)
        }



        assertTrue(combinedResult.isNotEmpty())
        val first = combinedResult.first()
        val second = combinedResult[1]
        assertTrue(first is ApiResult.Success)
        assertTrue(second is ApiResult.Success)

        if(first is ApiResult.Success && second is ApiResult.Success){

            /**
             * should expect this @see [latestTestRatesJson] & [currenciesTestJson]] to change or update test Json
             * CurrencyRates(base=USD,
             * rates=[Currency(code=AED, usdRate=3.67301, name=United Arab Emirates Dirham, conversion=0.0),
             * Currency(code=AFN, usdRate=88.932342, name=Afghan Afghani, conversion=0.0),
             * Currency(code=ALL, usdRate=116.861551, name=Albanian Lek, conversion=0.0),
             * Currency(code=AMD, usdRate=416.662312, name=Armenian Dram, conversion=0.0),
             * Currency(code=ANG, usdRate=1.801994, name=Netherlands Antillean Guilder, conversion=0.0)])
             */

            val currencyRate = convertToCurrencyRates(first.response,second.response)
            kotlin.test.assertTrue(currencyRate?.rates!!.isNotEmpty())
            mainActivityViewModel.parseRateResults(currencyRate)
        }

    }

    @Test
    fun testSearchCurrencies() {
        mainActivityViewModel.searchFilter("AE")
        assertTrue(mainActivityViewModel.bottomSheetCurrencyFilter.value.size == 1)
        mainActivityViewModel.searchFilter("")
        assertTrue(mainActivityViewModel.bottomSheetCurrencyFilter.value.size == mainActivityViewModel.currencyRates.value.result?.rates!!.size)
        mainActivityViewModel.searchFilter("AN") // search for AFN,ANG,Armenian,Afghan
        assertEquals(mainActivityViewModel.bottomSheetCurrencyFilter.value.size, 4)

    }

    @Test
    fun testCorrectCurrencyConversion(){
        mainActivityViewModel.calculateConversion(1.0)
        assertEquals(mainActivityViewModel.baseCurrency.value.code,"USD")
        assertEquals(mainActivityViewModel.currencyRates.value.result!!.rates.first().conversion,3.67301)
        //Change base currency and check conversion again
        mainActivityViewModel.setBaseCurrency(mainActivityViewModel.currencyRates.value.result!!.rates.last()) // ANG
        mainActivityViewModel.calculateConversion(1.0)
        assertEquals(mainActivityViewModel.currencyRates.value.result!!.rates.first().conversion.to2Dp(),2.04) // Rounded to 2 decimal place
    }

    @Test
    fun testWrongCurrencyConversion(){
        mainActivityViewModel.setBaseCurrency(mainActivityViewModel.currencyRates.value.result!!.rates.last()) // Base set to ANG
        mainActivityViewModel.calculateConversion(1.0)
        assertNotEquals(mainActivityViewModel.currencyRates.value.result!!.rates.first().conversion,3.67301) // Conversion should not be same
    }





}

fun Double.to2Dp():Double{
   return (this * 100.0).roundToInt() / 100.0
}

