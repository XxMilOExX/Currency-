package com.aj.currencycalculator.domain.currencyhistory

import com.aj.currencycalculator.data.mapper.ObjectMapper
import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.data.repository.CurrencyDataRepository
import com.aj.currencycalculator.domain.model.HistoricalData
import com.aj.currencycalculator.domain.model.HistoricalDataGroup
import com.aj.currencycalculator.util.DateTimeUtil
import com.aj.currencycalculator.util.extension.translateToError
import javax.inject.Inject

class CurrencyHistoryUseCaseImp @Inject constructor(
    private val repository: CurrencyDataRepository,
    private val dataMapper: ObjectMapper
) : CurrencyHistoryUseCase {

    override suspend fun getHistoryForDays(lastDays: Int): ResultData<HistoricalDataGroup?> {
        var result: HistoricalDataGroup? = null

        try {
            val yesterday = DateTimeUtil.getMillisOfLastXDays(1)
            val pastDate = DateTimeUtil.getMillisOfLastXDays(lastDays)
            if (yesterday != null && pastDate != null) {
                val hash = repository.getHistoricalData(pastDate, yesterday)
                val hashMap = LinkedHashMap<HistoricalData.Date, List<HistoricalData.Currency>>()
                val dataList = arrayListOf<HistoricalData>()
                if (!hash.isNullOrEmpty()) {
                    for ((key, list) in hash) {
                        val date = HistoricalData.Date(key)
                        dataList.add(date)
                        if (!list.isNullOrEmpty()) {
                            val listOfCurrency = dataMapper.currencyHistoryEntityToModel(list)
                            dataList.addAll(listOfCurrency)
                            hashMap[date] = listOfCurrency
                        }
                    }
                }
                result = HistoricalDataGroup(hashMap = hashMap, list = dataList)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return ex.translateToError()
        }
        return ResultData.Success(result)
    }

    override suspend fun insert(search: String) {
        repository.insertCurrencySearch(search)
    }
}
