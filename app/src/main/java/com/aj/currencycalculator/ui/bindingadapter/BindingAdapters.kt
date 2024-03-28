package com.aj.currencycalculator.ui.bindingadapter

import android.R
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import com.aj.currencycalculator.domain.model.Currency

object BindingAdapters : BaseObservable() {

    @BindingAdapter("loadCurrencies")
    @JvmStatic
    fun loadCurrencies(txt: AutoCompleteTextView, currencyCode: List<Currency>?) {
        currencyCode?.map { it.code }?.let {
            txt.setAdapter(
                ArrayAdapter(
                    txt.context,
                    R.layout.simple_list_item_activated_1,
                    it
                )
            )
        }
    }

//    @BindingAdapter("historyItems")
//    fun bindingHistoryListItems(recyclerView: RecyclerView, itemViewModels: List<ItemViewModel>?) {
//        val adapter = getOrCreateAdapter(recyclerView)
//        adapter.updateItems(itemViewModels)
//    }

//    private fun getOrCreateAdapter(recyclerView: RecyclerView): BindableRecyclerViewAdapter {
//        return if (recyclerView.adapter != null && recyclerView.adapter is BindableRecyclerViewAdapter) {
//            recyclerView.adapter as BindableRecyclerViewAdapter
//        } else {
//            val bindableRecyclerAdapter = BindableRecyclerViewAdapter()
//            recyclerView.adapter = bindableRecyclerAdapter
//            bindableRecyclerAdapter
//        }
//    }
}
