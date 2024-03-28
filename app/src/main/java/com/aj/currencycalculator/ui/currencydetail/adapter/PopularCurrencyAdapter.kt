package com.aj.currencycalculator.ui.currencydetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.aj.currencycalculator.R
import com.aj.currencycalculator.databinding.RowPopularCurrencyBinding
import com.aj.currencycalculator.domain.model.ConvertedConversion
import com.aj.currencycalculator.domain.model.Currency
import com.aj.currencycalculator.ui.base.BaseListAdapter

class PopularCurrencyAdapter(
    private val baseCurrency: Currency,
) : BaseListAdapter<ConvertedConversion, RowPopularCurrencyBinding>(diffCallback = diffCallback) {
    override fun createBinding(parent: ViewGroup, viewType: Int): RowPopularCurrencyBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.row_popular_currency,
            parent,
            false
        )
    }

    override fun bind(
        binding: RowPopularCurrencyBinding,
        item: ConvertedConversion,
        position: Int
    ) {
        binding.baseCurrency = baseCurrency
        binding.convertedCurrency = item
    }
}

val diffCallback: DiffUtil.ItemCallback<ConvertedConversion> =
    object : DiffUtil.ItemCallback<ConvertedConversion>() {
        override fun areItemsTheSame(
            oldItem: ConvertedConversion,
            newItem: ConvertedConversion
        ): Boolean {
            return false
        }

        override fun areContentsTheSame(
            oldItem: ConvertedConversion,
            newItem: ConvertedConversion
        ): Boolean {
            return false
        }
    }
