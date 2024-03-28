package com.aj.currencycalculator.data.mapper

import com.aj.currencycalculator.data.db.entity.CurrencyRateEntity
import com.aj.currencycalculator.data.network.model.currencylist.CurrencyRateNetwork
import com.aj.currencycalculator.domain.model.Currency
import com.aj.currencycalculator.domain.model.HistoricalData
import com.aj.currencycalculator.util.extension.toDBModel
import com.aj.currencycalculator.util.extension.toModel
import javax.inject.Inject

class ObjectMapperImp @Inject constructor() : ObjectMapper {

    override fun mapToEntity(listCurrency: List<CurrencyRateNetwork>): List<CurrencyRateEntity> {
        return listCurrency.map {
            it.toDBModel()
        }
    }

    override fun entityToModel(listCurrencyEntity: List<CurrencyRateEntity>): List<Currency> {
        return listCurrencyEntity.map {
            it.toModel()
        }
    }


    override fun currencyHistoryEntityToModel(list: List<CurrencyRateNetwork>): List<HistoricalData.Currency> {
        return list.map {
            it.toModel()
        }
    }
}
