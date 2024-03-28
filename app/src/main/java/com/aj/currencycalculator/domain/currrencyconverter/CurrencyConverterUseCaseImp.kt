package com.aj.currencycalculator.domain.currrencyconverter

import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.data.repository.CurrencyDataRepository
import com.aj.currencycalculator.util.extension.removeDotConvertToDouble
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CurrencyConverterUseCaseImp @Inject constructor(
    private val repository: CurrencyDataRepository
) : CurrencyConverterUseCase {

    override suspend fun calculateCurrency(
        input: String,
        baseCurrency: String,
        targetCurrency: String
    ): Flow<ResultData<Double>> = flow {
        val baseCurrencyList = repository.getCurrencyRateList(baseCurrency)
        val targetCurrencyList = repository.getCurrencyRateList(targetCurrency)
        try {
            if (!baseCurrencyList.isNullOrEmpty() && !targetCurrencyList.isNullOrEmpty()) {
                val rate = targetCurrencyList.first().rate / baseCurrencyList.first().rate
                val inputVal = input.removeDotConvertToDouble()
                inputVal?.let {
                    emit(ResultData.Success(it * rate))
                }
            } else {
                emit(ResultData.Failed("Currency not found - Please try refreshing data"))
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            emit(ResultData.Failed("An Error occurred in currency conversion"))
        }
    }
}
