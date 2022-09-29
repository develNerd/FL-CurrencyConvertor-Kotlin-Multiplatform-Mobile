package org.flepper.currencyconvertor

import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.flepper.currencyconvertor.data.network.Api
import org.flepper.currencyconvertor.data.network.ApiClient
import kotlin.test.Test
import kotlin.test.assertNotNull

class DITest:KoinTest {

    @Test
    fun `should inject my components`() {
        startKoin{
            modules(
                module {
                    single { ApiClient() }
                    single { Api(get()) }
                })
        }

        // directly request an instance
        val api = get<Api>()

        assertNotNull(api)
        stopKoin()
    }
}