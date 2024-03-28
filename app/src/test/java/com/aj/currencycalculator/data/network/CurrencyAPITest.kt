package com.aj.currencycalculator.data.network

import com.aj.currencycalculator.di.NetworkApiModuleTest
import com.aj.currencycalculator.testutil.MainDispatcherRule
import com.aj.currencycalculator.util.AppConstant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class CurrencyAPITest : NetworkApiModuleTest<CurrencyAPI>() {

    private lateinit var currencyAPI: CurrencyAPI

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutineRule = MainDispatcherRule()

    @Before
    fun setUp() {
        currencyAPI = createService(CurrencyAPI::class.java)
    }

    @Throws(IOException::class)
    @Test
    fun `test getCurrencies() returns success`() = runBlocking {
        // Given
        enqueueResponse("/api_latest_success_response.json")
        // Invoke
        val responseBody = requireNotNull(currencyAPI.getCurrencies())
        mockWebServer.takeRequest()
        // Then
        MatcherAssert.assertThat(
            responseBody.success,
            CoreMatchers.`is`(true)
        )
        MatcherAssert.assertThat(responseBody.timestamp, CoreMatchers.`is`(1675773243))
        MatcherAssert.assertThat(responseBody.base, CoreMatchers.`is`(AppConstant.BASE_CURRENCY))
    }

    @Throws(IOException::class)
    @Test
    fun `test getCurrencies() failure returns success false`() = runBlocking {
        // Given
        enqueueResponse("/api_latest_failure_response.json")
        // Invoke
        val responseBody = requireNotNull(currencyAPI.getCurrencies())
        mockWebServer.takeRequest()
        // Then
        MatcherAssert.assertThat(
            responseBody.success,
            CoreMatchers.`is`(false)
        )
    }

    @Throws(IOException::class)
    @Test
    fun `test getCurrencies() failure returns error code`() = runBlocking {
        // Given
        enqueueResponse("/api_latest_failure_response.json")
        // Invoke
        val responseBody = requireNotNull(currencyAPI.getCurrencies())
        mockWebServer.takeRequest()
        // Then
        MatcherAssert.assertThat(
            responseBody.error?.code,
            CoreMatchers.`is`(201)
        )
    }
}
