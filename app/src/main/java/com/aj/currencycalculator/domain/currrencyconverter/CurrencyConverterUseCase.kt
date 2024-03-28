package com.aj.currencycalculator.domain.currrencyconverter

import com.aj.currencycalculator.data.model.ResultData
import kotlinx.coroutines.flow.Flow

interface CurrencyConverterUseCase {
    suspend fun calculateCurrency(
        input: String,
        baseCurrency: String,
        targetCurrency: String
    ): Flow<ResultData<Double>>
}
