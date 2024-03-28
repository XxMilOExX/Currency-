package com.aj.currencycalculator.ui.currencydetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.databinding.FragmentCurrencyConversionDetailBinding
import com.aj.currencycalculator.domain.model.*
import com.aj.currencycalculator.ui.base.BaseFragment
import com.aj.currencycalculator.ui.currencydetail.adapter.HistoricalListAdapter
import com.aj.currencycalculator.ui.currencydetail.adapter.PopularCurrencyAdapter
import com.aj.currencycalculator.util.AppConstant
import com.aj.currencycalculator.util.extension.makeGone
import com.aj.currencycalculator.util.extension.makeVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CurrencyConversionDetailFragment : BaseFragment() {

    private val viewModel: CurrencyConversionDetailViewModel by viewModels()
    private lateinit var binding: FragmentCurrencyConversionDetailBinding
    private val args: CurrencyConversionDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentCurrencyConversionDetailBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        viewModel.popularCurrencies.observe(this.viewLifecycleOwner, popularCurrenciesObserver)
        viewModel.historicalData.observe(this.viewLifecycleOwner, historicalDataObserver)
    }

    private fun init() {
        val baseCurrency = args.baseCurrencyCode
        val baseInput = args.baseInputValue
        addObservers()
        if (!baseCurrency.isNullOrEmpty() && !baseInput.isNullOrEmpty()) {
            viewModel.loadPopularCurrencies(baseCurrency, baseInput, AppConstant.FAMOUS_CURRENCIES)
        }
    }

    private fun addObservers() {
        viewModel.searchHistoryList.observe(this.viewLifecycleOwner, searchHistoryObserver)
    }

    private val searchHistoryObserver = Observer<ResultData<List<SearchHistoryUI>>> {
        when (it) {
            is ResultData.Success -> {
                it.data?.let { _ ->
                }
            }

            is ResultData.Failed -> {
                showError(it.title, msg = it.message, parent = binding.parent)
            }

            is ResultData.Exception -> {
                showError(msg = it.msg, parent = binding.parent)
            }

            is ResultData.Loading -> {
            }

            else -> {
                showError(parent = binding.parent)
            }
        }
    }

    private val popularCurrenciesObserver = Observer<ResultData<PopularCurrenciesConversion>> {
        when (it) {
            is ResultData.Success -> {
                it.data?.let { data ->
                    if (data.convertedCurrencies.isNotEmpty()) {
                        loadPopularCurrenciesAdapter(it.data)
                    }
                }
            }

            is ResultData.Failed -> {
                showError(it.title, msg = it.message, parent = binding.parent)
            }

            is ResultData.Exception -> {
                showError(msg = it.msg, parent = binding.parent)
            }

            is ResultData.Loading -> {
            }

            else -> {
                showError(parent = binding.parent)
            }
        }
    }

    private val historicalDataObserver = Observer<ResultData<HistoricalDataGroup?>> {
        when (it) {
            is ResultData.Success -> {
                binding.progressHistory.makeGone()
                it.data?.let { data ->
                    val listHistory = data.list
                    if (listHistory.isNotEmpty())
                        loadHistoricalCurrenciesAdapter(listHistory, AppConstant.BASE_CURRENCY, 1.0)
                }
            }

            is ResultData.Failed -> {
                binding.progressHistory.makeGone()
                showError(it.title, msg = it.message, parent = binding.parent)
            }

            is ResultData.Exception -> {
                binding.progressHistory.makeGone()
                showError(msg = it.msg, parent = binding.parent)
            }

            is ResultData.Loading -> {
                binding.progressHistory.makeVisible()
            }

            else -> {
                binding.progressHistory.makeGone()
                showError(parent = binding.parent)
            }
        }
    }

    private fun loadPopularCurrenciesAdapter(popularCurrenciesConversionUI: PopularCurrenciesConversion) {
        if (popularCurrenciesConversionUI.convertedCurrencies.isNotEmpty()) {
            val adapter = PopularCurrencyAdapter(popularCurrenciesConversionUI.baseCurrency)
            binding.recyclerviewPopularCurrencis.layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
            binding.recyclerviewPopularCurrencis.setHasFixedSize(true)
            binding.recyclerviewPopularCurrencis.adapter = adapter
            adapter.submitList(popularCurrenciesConversionUI.convertedCurrencies)
        }
    }

    private fun loadHistoricalCurrenciesAdapter(
        historicalList: List<HistoricalData>?,
        baseCurrencyCode: String,
        baseCurrencyVal: Double
    ) {
        if (!historicalList.isNullOrEmpty()) {
            val adapter = HistoricalListAdapter(
                historicalList,
                Currency(code = baseCurrencyCode, baseCurrencyVal)
            )
            binding.recyclerViewHistoricaldata.layoutManager = LinearLayoutManager(
                context,
                RecyclerView.VERTICAL,
                false
            )
            binding.recyclerViewHistoricaldata.setHasFixedSize(true)
            binding.recyclerViewHistoricaldata.adapter = adapter
        }
    }
}
