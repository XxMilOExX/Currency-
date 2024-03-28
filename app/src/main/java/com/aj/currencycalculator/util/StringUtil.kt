package com.aj.currencycalculator.util

import android.content.Context
import com.aj.currencycalculator.R
import com.aj.currencycalculator.data.model.ResultData
import javax.inject.Inject

class StringUtil @Inject constructor(val context: Context) {

    fun getUnknownErrorMsg(): ResultData.Failed {
        return ResultData.Failed(
            title = context.getString(R.string.oh_snap),
            message = context.getString(R.string.error)
        )
    }
}
