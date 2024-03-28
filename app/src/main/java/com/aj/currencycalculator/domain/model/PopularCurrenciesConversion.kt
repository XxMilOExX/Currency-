package com.aj.currencycalculator.domain.model

data class PopularCurrenciesConversion(
    val baseCurrency: Currency,
    val convertedCurrencies: List<ConvertedConversion>
)
