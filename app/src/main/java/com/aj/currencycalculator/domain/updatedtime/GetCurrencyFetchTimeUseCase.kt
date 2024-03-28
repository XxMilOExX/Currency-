package com.aj.currencycalculator.domain.updatedtime

import kotlinx.coroutines.flow.Flow

interface GetCurrencyFetchTimeUseCase {
    fun getLastUpdateTime(): Flow<String?>
}
