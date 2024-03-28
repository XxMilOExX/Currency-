package com.aj.currencycalculator.domain.model

data class CurrencyConverterState(
    var baseCurrency: String?,
    var toCurrency: String?,
    var inputCurrency: String?
)
