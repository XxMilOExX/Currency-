package com.aj.currencycalculator.di

import com.aj.currencycalculator.data.db.dao.CurrencyHistoryDao
import com.aj.currencycalculator.data.db.dao.CurrencyRateDao
import com.aj.currencycalculator.data.db.dao.CurrencyRateUpdateTimeDao
import com.aj.currencycalculator.data.mapper.ObjectMapper
import com.aj.currencycalculator.data.mapper.ObjectMapperImp
import com.aj.currencycalculator.data.network.CurrencyAPI
import com.aj.currencycalculator.data.repository.CurrencyDataRepository
import com.aj.currencycalculator.data.repository.CurrencyDataRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providesDataRepository(
        currencyRateDAO: CurrencyRateDao,
        currencyTimeDao: CurrencyRateUpdateTimeDao,
        api: CurrencyAPI,
        currencySelectionHistoryDao: CurrencyHistoryDao,
        networkDaoMapper: ObjectMapper
    ): CurrencyDataRepository =
        CurrencyDataRepositoryImp(
            currencyRateDao = currencyRateDAO,
            currencyTimeDao = currencyTimeDao,
            currencySelectionHistoryDao = currencySelectionHistoryDao,
            api,
            networkDaoMapper
        )

    @Singleton
    @Provides
    fun providesNetworkMapper(): ObjectMapper = ObjectMapperImp()
}
