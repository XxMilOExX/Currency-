package com.aj.currencycalculator.di

import com.aj.currencycalculator.data.mapper.ObjectMapper
import com.aj.currencycalculator.data.repository.CurrencyDataRepository
import com.aj.currencycalculator.domain.currencyhistory.CurrencyHistoryUseCase
import com.aj.currencycalculator.domain.currencyhistory.CurrencyHistoryUseCaseImp
import com.aj.currencycalculator.domain.currrencyconverter.CurrencyConverterUseCase
import com.aj.currencycalculator.domain.currrencyconverter.CurrencyConverterUseCaseImp
import com.aj.currencycalculator.domain.popularcurrencies.PopularCurrenciesUseCase
import com.aj.currencycalculator.domain.popularcurrencies.PopularCurrenciesUseCaseImp
import com.aj.currencycalculator.domain.ratelist.GetSavedCurrencyRateListUseCase
import com.aj.currencycalculator.domain.ratelist.GetSavedCurrencyRateListUseCaseImp
import com.aj.currencycalculator.domain.updatedtime.GetCurrencyFetchTimeUseCase
import com.aj.currencycalculator.domain.updatedtime.GetCurrencyFetchTimeUseCaseImp
import com.aj.currencycalculator.domain.updaterates.RefreshCurrencyRatesUseCase
import com.aj.currencycalculator.domain.updaterates.RefreshCurrencyRatesUseCaseImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun providesFetchCurrencyRateUseCase(
        repository: CurrencyDataRepository,
        mapper: ObjectMapper
    ): RefreshCurrencyRatesUseCase =
        RefreshCurrencyRatesUseCaseImp(repository, mapper)

    @Singleton
    @Provides
    fun providesSavedCurrencyRateUseCase(
        repository: CurrencyDataRepository,
        mapper: ObjectMapper
    ): GetSavedCurrencyRateListUseCase =
        GetSavedCurrencyRateListUseCaseImp(repository, mapper)

    @Singleton
    @Provides
    fun provideLastFetchDateUseCase(
        repository: CurrencyDataRepository
    ): GetCurrencyFetchTimeUseCase =
        GetCurrencyFetchTimeUseCaseImp(repository)

    @Singleton
    @Provides
    fun providesCurrencyConversionUseCase(
        repository: CurrencyDataRepository
    ): CurrencyConverterUseCase =
        CurrencyConverterUseCaseImp(repository)

    @Singleton
    @Provides
    fun providesPopularCurrencyUseCase(
        repository: CurrencyDataRepository
    ): PopularCurrenciesUseCase =
        PopularCurrenciesUseCaseImp(repository)

    @Singleton
    @Provides
    fun provideConversionHistoryUseCase(
        repository: CurrencyDataRepository, mapper: ObjectMapper
    ): CurrencyHistoryUseCase =
        CurrencyHistoryUseCaseImp(repository, mapper)
}


