package com.aj.currencycalculator.domain.updaterates

import com.aj.currencycalculator.data.mapper.ObjectMapper
import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.data.repository.CurrencyDataRepository
import com.aj.currencycalculator.domain.model.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RefreshCurrencyRatesUseCaseImp @Inject constructor(
    private val repository: CurrencyDataRepository,
    private val layersObjectMapper: ObjectMapper
) : RefreshCurrencyRatesUseCase {

    override fun refreshCurrencyRateFromAPI(): Flow<ResultData<List<Currency>>> = flow {
        when (val result = repository.updateDataFromNetwork()) {
            is ResultData.Success -> {
                result.data?.let {
                    emit(ResultData.Success(layersObjectMapper.entityToModel(result.data)))
                }
            }

            is ResultData.Failed -> {
                emit(result)
            }
            is ResultData.Exception -> {
                result.exception?.printStackTrace()
                emit(result)
            }
            else -> {
                emit(ResultData.Failed("Oh Snap! An unknown Error occurred"))
            }
        }
    }
}
