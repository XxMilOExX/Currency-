package com.aj.currencycalculator.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aj.currencycalculator.data.db.entity.CurrencyHistoryEntity

@Dao
interface CurrencyHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: CurrencyHistoryEntity)

    @Query("SELECT DISTINCT currencyCode FROM CurrencyHistoryEntity")
    suspend fun getHistory(): List<CurrencyHistoryEntity>?
}
