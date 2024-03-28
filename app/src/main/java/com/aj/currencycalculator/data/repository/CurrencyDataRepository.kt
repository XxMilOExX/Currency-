package com.aj.currencycalculator.data.repository

import com.aj.currencycalculator.data.db.entity.CurrencyRateEntity
import com.aj.currencycalculator.data.db.entity.CurrencyRateUpdateTimeEntity
import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.data.network.model.currencylist.CurrencyRateNetwork
import java.util.*

interface CurrencyDataRepository {
    suspend fun updateDataFromNetwork(): ResultData<List<CurrencyRateEntity>>
    suspend fun getCurrencyRateList(): List<CurrencyRateEntity>
    suspend fun getCurrencyUpdateTime(): CurrencyRateUpdateTimeEntity
    suspend fun getCurrencyRateList(currencyCode: String): List<CurrencyRateEntity>?
    suspend fun getCurrenciesRateList(currencyCodes: ArrayList<String>): List<CurrencyRateEntity>?
    suspend fun getHistoricalData(
        from: Date,
        to: Date
    ): LinkedHashMap<String, List<CurrencyRateNetwork>?>?

    suspend fun insertCurrencySearch(currencyCode: String)
}
