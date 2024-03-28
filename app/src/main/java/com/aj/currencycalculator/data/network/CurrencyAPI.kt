package com.aj.currencycalculator.data.network

import com.aj.currencycalculator.data.network.model.currencylist.CurrencyResponse
import com.aj.currencycalculator.data.network.model.timeseries.TimeSeriesResponse
import com.aj.currencycalculator.util.AppConstant
import com.aj.currencycalculator.util.NetworkConstant
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyAPI {
    @GET(NetworkConstant.GET_LATEST)
    suspend fun getCurrencies(
        @Query("base") source: String = AppConstant.BASE_CURRENCY
    ): CurrencyResponse

    @GET(NetworkConstant.TIME_SERIES)
    suspend fun getTimeSeries(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("symbols") csvSymbols: String,
        @Query("base") source: String = AppConstant.BASE_CURRENCY
    ): TimeSeriesResponse
}
