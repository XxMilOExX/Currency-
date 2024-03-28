package com.aj.currencycalculator.domain.model

import java.util.Date

sealed class SearchHistoryUI {
    class DateHeader(var date: String?) : SearchHistoryUI()
    class SearchHistory(
        var dateTime: Date,
        var baseCurrency: String,
        var toCurrency: String,
        var rate: Double
    ) : SearchHistoryUI()
}
