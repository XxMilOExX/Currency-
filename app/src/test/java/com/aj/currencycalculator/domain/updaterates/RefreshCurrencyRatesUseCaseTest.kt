package com.aj.currencycalculator.domain.updaterates

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.aj.currencycalculator.data.mapper.ObjectMapperImp
import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.data.repository.FakeCurrencyDataRepository
import com.aj.currencycalculator.testutil.MockTestUtil
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RefreshCurrencyRatesUseCaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fakeDataRepo: FakeCurrencyDataRepository
    private lateinit var refreshCurrencyRatesUseCase: RefreshCurrencyRatesUseCase

    @Before
    fun start() {
        fakeDataRepo = FakeCurrencyDataRepository()
        refreshCurrencyRatesUseCase =
            RefreshCurrencyRatesUseCaseImp(fakeDataRepo, ObjectMapperImp())
    }

    @Test
    fun `Calling updateDataFromNetwork with no Network returns Exception`() = runBlocking {
        fakeDataRepo.setShouldReturnNetworkError(true)
        refreshCurrencyRatesUseCase.refreshCurrencyRateFromAPI().test {
            val emission = awaitItem()
            assertThat(emission).isInstanceOf(ResultData.Exception::class.java)
            awaitComplete()
        }
    }

    @Test
    fun `Calling updateDataFromNetwork with Network returns Success`() = runBlocking {
        fakeDataRepo.setShouldReturnNetworkError(false)
        refreshCurrencyRatesUseCase.refreshCurrencyRateFromAPI().test {
            val emission = awaitItem()
            assertThat(emission).isInstanceOf(ResultData.Success::class.java)
            awaitComplete()
        }
    }

    @Test
    fun `Calling updateDataFromNetwork with Network returns non null list`() = runBlocking {
        // Given
        val list = MockTestUtil.getSampleCurrencyRateList()
        fakeDataRepo.setShouldReturnNetworkError(false)
        fakeDataRepo.setCurrencyRateList(list)
        // Invoke
        refreshCurrencyRatesUseCase.refreshCurrencyRateFromAPI().test {
            val flowItem = (awaitItem() as ResultData.Success).data
            // Then
            MatcherAssert.assertThat(flowItem, CoreMatchers.notNullValue())
            MatcherAssert.assertThat(flowItem!!.size, CoreMatchers.`is`(list.size))
            awaitComplete()
        }
    }
}
