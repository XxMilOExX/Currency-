package com.aj.currencycalculator.data.network.model

import com.google.gson.annotations.SerializedName

data class CurrencyAPIError(
    @SerializedName("code") var code: Int = 0,
    @SerializedName("info") var info: String? = null,
    @SerializedName("type")
    var type: String? = null
)
