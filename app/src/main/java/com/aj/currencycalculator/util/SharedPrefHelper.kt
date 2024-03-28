package com.aj.currencycalculator.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class SharedPrefHelper @SuppressLint("CommitPrefEdits") constructor(
    mContext: Context
) {
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var prefsEditor: SharedPreferences.Editor

    init {
        sharedPrefs = mContext.getSharedPreferences(
            AppConstant.SHARED_PREF,
            Activity.MODE_PRIVATE
        )
        prefsEditor = sharedPrefs.edit()
    }

    fun setCurrencyRateSaved(isSaved: Boolean) {
        prefsEditor.putBoolean(IS_CURRENCY_CONVERSION_SAVED_FIRST_TIME, isSaved)
        prefsEditor.commit()
    }

    fun isCurrencyRateSavedOnce(): Boolean {
        return sharedPrefs.getBoolean(IS_CURRENCY_CONVERSION_SAVED_FIRST_TIME, false)
    }

    companion object {
        const val IS_CURRENCY_CONVERSION_SAVED_FIRST_TIME = "Data_saved"
    }
}
