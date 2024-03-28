package com.aj.currencycalculator.data.repository

import com.aj.currencycalculator.data.db.dao.CurrencyHistoryDao
import com.aj.currencycalculator.data.db.dao.CurrencyRateDao
import com.aj.currencycalculator.data.db.dao.CurrencyRateUpdateTimeDao
import com.aj.currencycalculator.data.db.entity.CurrencyHistoryEntity
import com.aj.currencycalculator.data.db.entity.CurrencyRateEntity
import com.aj.currencycalculator.data.db.entity.CurrencyRateUpdateTimeEntity
import com.aj.currencycalculator.data.mapper.ObjectMapper
import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.data.network.CurrencyAPI
import com.aj.currencycalculator.data.network.model.currencylist.CurrencyRateNetwork
import com.aj.currencycalculator.data.network.model.currencylist.toListOfRates
import com.aj.currencycalculator.data.network.model.timeseries.DateRateJson
import com.aj.currencycalculator.data.network.model.timeseries.TimeSeriesResponse
import com.aj.currencycalculator.data.network.model.timeseries.toDateListJson
import com.aj.currencycalculator.data.network.model.timeseries.toListOfRates
import com.aj.currencycalculator.util.DateTimeUtil
import com.aj.currencycalculator.util.extension.translateToError
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Local Database is ultimate source of truth
 */

@Singleton
class CurrencyDataRepositoryImp @Inject constructor(
    private val currencyRateDao: CurrencyRateDao,
    private val currencyTimeDao: CurrencyRateUpdateTimeDao,
    private val currencySelectionHistoryDao: CurrencyHistoryDao,
    private val currencyConverterAPI: CurrencyAPI,
    private val networkDaoMapper: ObjectMapper
) : CurrencyDataRepository {

    override suspend fun updateDataFromNetwork(): ResultData<List<CurrencyRateEntity>> {
        try {
            val response = currencyConverterAPI.getCurrencies()
            if (response.success) {
                val result = response.toListOfRates()
                result?.let {
                    val daoRates = networkDaoMapper.mapToEntity(it)
                    currencyTimeDao.insert(
                        CurrencyRateUpdateTimeEntity(
                            "1",
                            response.timestamp
                        )
                    )
                    currencyRateDao.insert(daoRates)
                    return ResultData.Success(daoRates)
                } ?: run {
                    return ResultData.Failed(
                        title = "Oh Snap!",
                        message = "Server seems busy - Please try again later"
                    )
                }
            } else {
                return ResultData.Failed(
                    title = "API Error",
                    message = "Error code ${response.error?.code ?: " Reason - ${response.error}"},"
                )
            }
        } catch (ex: Exception) {
            return ex.translateToError()
        }
    }

    override suspend fun getHistoricalData(
        from: Date,
        to: Date
    ): LinkedHashMap<String, List<CurrencyRateNetwork>?> {
        val apiResponse = callTimeSeriesAPI(from, to)
        var result = LinkedHashMap<String, List<CurrencyRateNetwork>?>()
        apiResponse?.let { response ->
            if (response.success) {
                val dateListJson = response.toDateListJson()
                result = getDateCurrencyRatesHashMap(dateListJson)
            }
        }
        return result
    }

    /***
     * Maps Json of currencies w.r.t date
     * returns@ HashMap of <Date,ListCurrencyRateNetwork>
     */
    private fun getDateCurrencyRatesHashMap(list: List<DateRateJson>?): LinkedHashMap<String, List<CurrencyRateNetwork>?> {
        val result = LinkedHashMap<String, List<CurrencyRateNetwork>?>()
        list?.let {
            for (dateJsonObj in list) {
                val date = dateJsonObj.date
                val currencyList = dateJsonObj.toListOfRates()
                result[date] = currencyList
            }
        }
        return result
    }

    private suspend fun callTimeSeriesAPI(from: Date, to: Date): TimeSeriesResponse? {
        val rate = currencySelectionHistoryDao.getHistory()
        val startDate = DateTimeUtil.getAPIDate(from)
        val endDate = DateTimeUtil.getAPIDate(to)
        if (!rate.isNullOrEmpty() && !startDate.isNullOrEmpty() && !endDate.isNullOrEmpty()) {
            val listOfCurrencies = rate.joinToString { it.currencyCode }
            return currencyConverterAPI.getTimeSeries(
                startDate = startDate,
                endDate = endDate,
                listOfCurrencies
            )
        }
        return null
    }

    override suspend fun insertCurrencySearch(currencyCode: String) {
        currencySelectionHistoryDao.insert(CurrencyHistoryEntity(currencyCode))
    }

    override suspend fun getCurrencyRateList(): List<CurrencyRateEntity> =
        currencyRateDao.getCurrencyList()

    override suspend fun getCurrencyUpdateTime(): CurrencyRateUpdateTimeEntity =
        currencyTimeDao.findLastTimeStamp()

    override suspend fun getCurrencyRateList(currencyCode: String): List<CurrencyRateEntity>? =
        currencyRateDao.getCurrencyRate(currencyCode)

    override suspend fun getCurrenciesRateList(currencyCodes: ArrayList<String>): List<CurrencyRateEntity>? =
        currencyRateDao.getCurrencyRate(currencyCodes)
}
