package com.aj.currencycalculator.data.network.model.timeseries

import com.aj.currencycalculator.data.network.model.CurrencyAPIError
import com.aj.currencycalculator.data.network.model.currencylist.CurrencyRateNetwork
import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TimeSeriesResponse(
    @SerializedName("success")
    @Expose
    val success: Boolean = false,
    @SerializedName("timeseries")
    @Expose
    val timeSeries: Boolean = false,
    @SerializedName("start_date")
    @Expose
    val startDate: String?,
    @SerializedName("end_date")
    @Expose
    val endDate: String?,
    @SerializedName("base")
    @Expose
    val base: String?,
    @SerializedName("rates")
    @Expose
    val rates: JsonObject?,
    @SerializedName("error")
    @Expose
    val error: CurrencyAPIError?
)

data class DateRateJson(val date: String, val ratesMap: JsonObject?)

fun TimeSeriesResponse.toDateListJson(): List<DateRateJson>? {
    return this.rates?.asMap()?.map {
        DateRateJson(date = it.key, ratesMap = it.value.asJsonObject)
    }
}

fun DateRateJson.toListOfRates(): List<CurrencyRateNetwork>? {
    return this.ratesMap?.asMap()?.map {
        CurrencyRateNetwork(
            code = it.key,
            rate = it.value.asDouble
        )
    }
}
