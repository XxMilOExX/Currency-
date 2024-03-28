package com.aj.currencycalculator.domain.ratelist

import com.aj.currencycalculator.domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface GetSavedCurrencyRateListUseCase {
    fun getSavedCurrencyList(): Flow<List<Currency>>
}
