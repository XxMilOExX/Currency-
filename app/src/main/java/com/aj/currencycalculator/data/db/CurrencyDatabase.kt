package com.aj.currencycalculator.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.aj.currencycalculator.data.db.converter.RoomConverter
import com.aj.currencycalculator.data.db.dao.CurrencyHistoryDao
import com.aj.currencycalculator.data.db.dao.CurrencyRateDao
import com.aj.currencycalculator.data.db.dao.CurrencyRateUpdateTimeDao
import com.aj.currencycalculator.data.db.entity.CurrencyHistoryEntity
import com.aj.currencycalculator.data.db.entity.CurrencyRateEntity
import com.aj.currencycalculator.data.db.entity.CurrencyRateUpdateTimeEntity

@Database(
    entities = [CurrencyRateEntity::class, CurrencyRateUpdateTimeEntity::class, CurrencyHistoryEntity::class],
    version = 1
)
@TypeConverters(RoomConverter::class)
abstract class CurrencyDatabase : RoomDatabase() {
    abstract fun getCurrencyDao(): CurrencyRateDao
    abstract fun getCurrencyUpdateTime(): CurrencyRateUpdateTimeDao
    abstract fun getCurrencyHistoryDao(): CurrencyHistoryDao
}
