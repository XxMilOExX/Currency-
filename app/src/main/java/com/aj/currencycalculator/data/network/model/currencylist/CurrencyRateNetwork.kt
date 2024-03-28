package com.aj.currencycalculator.data.network.model.currencylist

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CurrencyRateNetwork(
    @SerializedName("code")
    @Expose
    val code: String?,
    @SerializedName("rate")
    @Expose val rate: Double = 0.0
)
