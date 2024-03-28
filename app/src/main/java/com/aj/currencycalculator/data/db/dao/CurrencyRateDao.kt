package com.aj.currencycalculator.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aj.currencycalculator.data.db.entity.CurrencyRateEntity

@Dao
interface CurrencyRateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: List<CurrencyRateEntity>)

    @Query("select * from CurrencyRateEntity")
    suspend fun getCurrencyList(): List<CurrencyRateEntity>

    @Query("SELECT EXISTS(SELECT * FROM CurrencyRateEntity)")
    suspend fun hasData(): Boolean

    @Query("SELECT * FROM CurrencyRateEntity WHERE code=:currencyCode ")
    suspend fun getCurrencyRate(
        currencyCode: String,
    ): List<CurrencyRateEntity>

    @Query("SELECT * FROM CurrencyRateEntity WHERE code IN (:currencyCodes)")
    suspend fun getCurrencyRate(
        currencyCodes: List<String>,
    ): List<CurrencyRateEntity>
}
