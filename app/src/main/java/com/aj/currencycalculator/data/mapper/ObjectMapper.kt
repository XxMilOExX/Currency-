package com.aj.currencycalculator.data.mapper

import com.aj.currencycalculator.data.db.entity.CurrencyRateEntity
import com.aj.currencycalculator.data.network.model.currencylist.CurrencyRateNetwork
import com.aj.currencycalculator.domain.model.Currency
import com.aj.currencycalculator.domain.model.HistoricalData

interface ObjectMapper {
    fun mapToEntity(listCurrency: List<CurrencyRateNetwork>): List<CurrencyRateEntity>
    fun entityToModel(listCurrencyEntity: List<CurrencyRateEntity>): List<Currency>
    fun currencyHistoryEntityToModel(list: List<CurrencyRateNetwork>): List<HistoricalData.Currency>
}
