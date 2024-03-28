package com.aj.currencycalculator.domain.model

data class ConvertedConversion(
    val code: String?,
    val rate: Double = 0.0, //
    val convertedRate: Double = 0.0
)
