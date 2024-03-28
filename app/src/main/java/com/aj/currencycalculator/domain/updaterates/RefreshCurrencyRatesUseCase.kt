package com.aj.currencycalculator.domain.updaterates

import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.domain.model.Currency
import kotlinx.coroutines.flow.Flow

/**
 * Refresh Currency Rates from the Web
 *
 */
interface RefreshCurrencyRatesUseCase {
    fun refreshCurrencyRateFromAPI(): Flow<ResultData<List<Currency>>>
}
