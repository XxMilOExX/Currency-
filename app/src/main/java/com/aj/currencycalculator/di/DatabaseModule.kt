package com.aj.currencycalculator.di

import android.content.Context
import androidx.room.Room
import com.aj.currencycalculator.data.db.CurrencyDatabase
import com.aj.currencycalculator.util.AppConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun providesCurrencyRateDao(currencyDatabase: CurrencyDatabase) =
        currencyDatabase.getCurrencyDao()

    @Singleton
    @Provides
    fun providesCurrencyUpdateTimeDao(currencyDatabase: CurrencyDatabase) =
        currencyDatabase.getCurrencyUpdateTime()

    @Singleton
    @Provides
    fun providesCurrencyConverterDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CurrencyDatabase::class.java, AppConstant.DATABASE_NAME)
            .build()


    @Singleton
    @Provides
    fun provideCurrencyHistoryDao(currencyDatabase: CurrencyDatabase) =
        currencyDatabase.getCurrencyHistoryDao()
}
