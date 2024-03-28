package com.aj.currencycalculator.ui.currencydetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aj.currencycalculator.R
import com.aj.currencycalculator.databinding.RowSearchHistoryBinding
import com.aj.currencycalculator.databinding.RowSearchHistoryHeaderBinding
import com.aj.currencycalculator.domain.model.Currency
import com.aj.currencycalculator.domain.model.HistoricalData

class HistoricalListAdapter(
    private val list: List<HistoricalData>?,
    private val baseCurrency: Currency,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_SEARCH_HISTORY = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_HEADER) {
            ViewHolderHeader(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.row_search_history_header,
                    parent,
                    false
                )
            )
        } else {
            ViewHolderSearchHistory(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.row_search_history,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val data = list?.get(position)) {
            is HistoricalData.Date -> {
                (holder as ViewHolderHeader).bind(data)
            }

            is HistoricalData.Currency -> {
                (holder as ViewHolderSearchHistory).bind(data, baseCurrency)
            }
            else -> {
            }
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return when (list?.get(position)) {
            is HistoricalData.Date -> TYPE_HEADER
            else -> TYPE_SEARCH_HISTORY
        }
    }

    class ViewHolderHeader(itemRowBinding: RowSearchHistoryHeaderBinding) :
        RecyclerView.ViewHolder(itemRowBinding.root) {
        var itemRowBinding: RowSearchHistoryHeaderBinding

        init {
            this.itemRowBinding = itemRowBinding
        }

        fun bind(dataHeader: HistoricalData.Date) {
            itemRowBinding.header = dataHeader
            itemRowBinding.executePendingBindings()
        }
    }

    class ViewHolderSearchHistory(itemRowBinding: RowSearchHistoryBinding) :
        RecyclerView.ViewHolder(itemRowBinding.root) {
        var itemRowBinding: RowSearchHistoryBinding

        init {
            this.itemRowBinding = itemRowBinding
        }

        fun bind(currency: HistoricalData.Currency, baseCurrency: Currency) {
            itemRowBinding.history = currency
            itemRowBinding.baseCurrency = baseCurrency
            itemRowBinding.executePendingBindings()
        }
    }
}
