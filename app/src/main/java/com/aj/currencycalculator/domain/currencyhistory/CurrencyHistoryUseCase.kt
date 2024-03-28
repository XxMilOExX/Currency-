package com.aj.currencycalculator.domain.currencyhistory

import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.domain.model.HistoricalDataGroup

interface CurrencyHistoryUseCase {
    suspend fun getHistoryForDays(lastDays: Int): ResultData<HistoricalDataGroup?>
    suspend fun insert(currencyCode: String)
}
