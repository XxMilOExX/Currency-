package com.aj.currencycalculator.domain.ratelist

import com.aj.currencycalculator.data.mapper.ObjectMapper
import com.aj.currencycalculator.data.repository.CurrencyDataRepository
import com.aj.currencycalculator.domain.model.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Get Local Currency Rate list
 */
class GetSavedCurrencyRateListUseCaseImp @Inject constructor(
    private val repository: CurrencyDataRepository,
    private val layersObjectMapper: ObjectMapper
) : GetSavedCurrencyRateListUseCase {

    override fun getSavedCurrencyList(): Flow<List<Currency>> = flow {
        emit(layersObjectMapper.entityToModel(repository.getCurrencyRateList()))
    }
}
