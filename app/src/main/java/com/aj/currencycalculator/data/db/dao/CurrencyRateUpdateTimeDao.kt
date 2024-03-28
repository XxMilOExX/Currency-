package com.aj.currencycalculator.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aj.currencycalculator.data.db.entity.CurrencyRateUpdateTimeEntity

@Dao
interface CurrencyRateUpdateTimeDao {
    @Query("SELECT * FROM CurrencyRateUpdateTimeEntity WHERE id='1'")
    fun findLastTimeStamp(): CurrencyRateUpdateTimeEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: CurrencyRateUpdateTimeEntity)
}
