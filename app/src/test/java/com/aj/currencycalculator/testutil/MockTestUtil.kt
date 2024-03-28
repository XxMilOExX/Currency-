package com.aj.currencycalculator.testutil

import com.aj.currencycalculator.data.db.entity.CurrencyRateEntity
import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.util.extension.translateToError
import java.io.IOException

object MockTestUtil {

    fun getSampleCurrencyRateList(): List<CurrencyRateEntity> {
        return arrayListOf(
            CurrencyRateEntity("AFN", 96.589165),
            CurrencyRateEntity("ANG", 1.930328)
        )
    }

    fun getNoInternetError(): ResultData.Exception {
        return IOException().translateToError()
    }
}
