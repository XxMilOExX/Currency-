package com.aj.currencycalculator.domain.popularcurrencies

import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.data.repository.CurrencyDataRepository
import com.aj.currencycalculator.domain.model.ConvertedConversion
import com.aj.currencycalculator.util.extension.removeDotConvertToDouble
import com.aj.currencycalculator.util.extension.translateToError
import javax.inject.Inject

class PopularCurrenciesUseCaseImp @Inject constructor(
    private val repository: CurrencyDataRepository
) :
    PopularCurrenciesUseCase {
    override suspend fun getPopularCurrencies(
        baseCurrency: String,
        userInputOfBaseCurrency: String,
        resultCurrencyCodes: ArrayList<String>
    ): ResultData<List<ConvertedConversion>?> {
        val result = arrayListOf<ConvertedConversion>()
        try {
            val arrayList = repository.getCurrenciesRateList(resultCurrencyCodes)
            val baseCurrencyList = repository.getCurrencyRateList(baseCurrency)
            arrayList?.let {
                for (targetCurrency in it) {
                    if (!baseCurrencyList.isNullOrEmpty()) {
                        val rate = targetCurrency.rate / baseCurrencyList.first().rate
                        val userInput = userInputOfBaseCurrency.removeDotConvertToDouble()
                        userInput?.let { userInputVal ->
                            val convertedRate = userInputVal * rate
                            result.add(
                                ConvertedConversion(
                                    targetCurrency.code,
                                    targetCurrency.rate,
                                    convertedRate
                                )
                            )
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return ex.translateToError()
        }
        return ResultData.Success(result)
    }
}
