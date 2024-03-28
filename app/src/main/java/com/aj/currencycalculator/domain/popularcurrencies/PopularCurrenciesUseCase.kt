package com.aj.currencycalculator.domain.popularcurrencies

import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.domain.model.ConvertedConversion

interface PopularCurrenciesUseCase {
    suspend fun getPopularCurrencies(
        baseCurrency: String,
        userInputOfBaseCurrency: String,
        resultCurrencyCodes: ArrayList<String>
    ): ResultData<List<ConvertedConversion>?>
}
