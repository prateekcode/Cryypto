package com.prateekcode.cryypto.adapter.diffutils

import androidx.recyclerview.widget.DiffUtil
import com.prateekcode.cryypto.model.Currencies

class FavoriteCurrencyDiffUtil: DiffUtil.ItemCallback<Currencies>() {

    override fun areItemsTheSame(oldItem: Currencies, newItem: Currencies): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Currencies, newItem: Currencies): Boolean {
        return oldItem.id == newItem.id
    }
}