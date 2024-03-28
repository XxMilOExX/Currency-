package com.aj.currencycalculator.ui.historicalchart

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.aj.currencycalculator.data.model.ResultData
import com.aj.currencycalculator.databinding.FragmentHistoricalChartBinding
import com.aj.currencycalculator.domain.model.HistoricalData
import com.aj.currencycalculator.domain.model.HistoricalDataGroup
import com.aj.currencycalculator.ui.base.BaseFragment
import com.aj.currencycalculator.ui.currencydetail.CurrencyConversionDetailViewModel
import com.aj.currencycalculator.util.AppConstant
import com.aj.currencycalculator.util.extension.makeGone
import com.aj.currencycalculator.util.extension.makeVisible
import com.aj.currencycalculator.util.extension.toThreeDecimal
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import java.util.*
import kotlin.collections.ArrayList

class HistoricalChartFragment : BaseFragment() {

    private lateinit var binding: FragmentHistoricalChartBinding
    private val viewModel: CurrencyConversionDetailViewModel? by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentHistoricalChartBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        viewModel?.historicalData?.observe(this.viewLifecycleOwner, historicalDataObserver)
    }

    private val historicalDataObserver = Observer<ResultData<HistoricalDataGroup?>> {
        when (it) {
            is ResultData.Success -> {
                binding.progressbar.makeGone()
                it.data?.let { data ->
                    binding.barChart.visibility = View.VISIBLE
                    chartSettings()
                    barDataSet(data.hashMap)
                }
            }

            is ResultData.Failed -> {
                binding.progressbar.makeGone()
                showError(it.title, msg = it.message, parent = binding.parent)
            }

            is ResultData.Exception -> {
                binding.progressbar.makeGone()
                showError(msg = it.msg, parent = binding.parent)
            }

            is ResultData.Loading -> {
                binding.progressbar.makeVisible()
            }

            else -> {
                binding.progressbar.makeGone()
                showError(parent = binding.parent)
            }
        }
    }

    private fun barDataSet(hashMap: LinkedHashMap<HistoricalData.Date, List<HistoricalData.Currency>>) {
        if (hashMap.isEmpty())
            return
        val groupSpace = 0.08f
        val barSpace = 0.05f
        val barWidth = 0.5f
        val (xAxisValues, chart) = prepareChartData(hashMap)
        binding.barChart.xAxis.axisMinimum = 0f
        val groupCount = hashMap.size
        binding.barChart.data = chart
        binding.barChart.data.barWidth = barWidth
        binding.barChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)
        binding.barChart.xAxis.labelCount = xAxisValues.size
        binding.barChart.xAxis.position = XAxis.XAxisPosition.TOP
        binding.barChart.xAxis.axisMinimum = 0f
        binding.barChart.groupBars(0f, groupSpace, barSpace)
        binding.barChart.xAxis.axisMaximum =
            0f + binding.barChart.barData.getGroupWidth(groupSpace, barSpace) * groupCount
        binding.barChart.invalidate()
        binding.barChart.description.text = "Base Currency: 1${AppConstant.BASE_CURRENCY}"
        binding.barChart.description.textSize = 11f
    }

    private fun prepareChartData(hashMap: LinkedHashMap<HistoricalData.Date, List<HistoricalData.Currency>>): Pair<ArrayList<String>, BarData> {
        val currencyCodeMap = LinkedHashMap<String?, ArrayList<BarEntry>>()
        for ((_, list) in hashMap) {
            for (currencyData in list) {
                val barEntryForCurrency = ArrayList<BarEntry>()
                currencyCodeMap[currencyData.code] = barEntryForCurrency
            }
            break
        }

        // Iterate Main Map to prepare BarEntries for every currency
        val xAxisTitles = ArrayList<String>()
        for ((key, list) in hashMap) {
            var index = 0f
            for (currency in list) {
                val mapEntries = currencyCodeMap[currency.code]
                mapEntries?.add(BarEntry(index, currency.rate.toThreeDecimal().toFloat()))
                mapEntries?.let {
                    currencyCodeMap.put(currency.code, mapEntries)
                }
                index++
            }
            xAxisTitles.add(key.date ?: "")
        }
        val chartData = BarData()
        // Prepare final List of Sets
        for ((key, list) in currencyCodeMap) {
            val dataSet = BarDataSet(list, key)
            dataSet.color = getRandomRGBColor()
            chartData.addDataSet(dataSet)
        }
        chartData.setValueFormatter(LargeValueFormatter())
        return Pair(xAxisTitles, chartData)
    }

    private fun chartSettings() {
        binding.barChart.description.isEnabled = true
        binding.barChart.setPinchZoom(false)
        binding.barChart.setDrawBarShadow(false)
        binding.barChart.setDrawGridBackground(false)

        // Set Legend
        val l: Legend = binding.barChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(true)
//        l.typeface = tfLight
        l.yOffset = 0f
        l.xOffset = 10f
        l.yEntrySpace = 0f
        l.textSize = 10f

        // Set X-Axis
        val xAxis: XAxis = binding.barChart.xAxis
        xAxis.granularity = 1f
        xAxis.setCenterAxisLabels(true)
        xAxis.valueFormatter =
            IAxisValueFormatter { value, _ -> value.toInt().toString() }
    }

    private fun getRandomRGBColor(): Int {
        val rnd = Random()
        return Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }
}
