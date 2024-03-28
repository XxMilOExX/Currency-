package com.aj.currencycalculator.domain.model

class HistoricalDataGroup(
    val hashMap: LinkedHashMap<HistoricalData.Date, List<HistoricalData.Currency>>,
    val list: List<HistoricalData>
)

sealed class HistoricalData {
    class Date(var date: String?) : HistoricalData()
    class Currency(val code: String?, val rate: Double = 0.0) : HistoricalData()
}
