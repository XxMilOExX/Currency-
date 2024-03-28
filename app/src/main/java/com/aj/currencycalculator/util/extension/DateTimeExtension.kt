package com.aj.currencycalculator.util.extension

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("SimpleDateFormat")
fun Long.toDateTime(): String? {
    val formatter = SimpleDateFormat("MM/dd/yyyy HH:mm a", Locale.US)
    return formatter.format(Date(this))
}
