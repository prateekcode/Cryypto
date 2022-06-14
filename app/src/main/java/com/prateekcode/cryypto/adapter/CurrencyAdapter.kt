package com.prateekcode.cryypto.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.prateekcode.cryypto.R
import com.prateekcode.cryypto.databinding.ItemCryptoBinding
import com.prateekcode.cryypto.model.Currencies
import com.prateekcode.cryypto.utils.loadSvg
import com.prateekcode.cryypto.utils.roundOffDecimal
import com.prateekcode.cryypto.utils.setDrawableColor
import com.prateekcode.cryypto.viewmodel.HomeViewModel

class CurrencyAdapter(
    private val homeViewModel: HomeViewModel,
    private val onItemClick: (Currencies) -> Unit
) :
    PagingDataAdapter<Currencies, CurrencyAdapter.CurrencyViewHolder>(CURRENCY_COMPARATOR) {

    companion object {
        private val CURRENCY_COMPARATOR = object : DiffUtil.ItemCallback<Currencies>() {
            override fun areContentsTheSame(oldItem: Currencies, newItem: Currencies): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areItemsTheSame(oldItem: Currencies, newItem: Currencies): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onBindViewHolder(holder: CurrencyAdapter.CurrencyViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null)
            holder.bind(currentItem)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CurrencyAdapter.CurrencyViewHolder {
        val binding = ItemCryptoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding)
    }

    inner class CurrencyViewHolder(private val binding: ItemCryptoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currency: Currencies) {
            binding.apply {
                tvCoinName.text = currency.name
                tvCoinId.text = currency.id
                tvCoinCurrentPrice.text = homeViewModel.context.getString(
                    R.string.price_placeholder,
                    currency.price.roundOffDecimal().toString()
                )
                if (currency.oneDay?.priceChangePct!! >= 0)
                    tvCoinGrowPercent.apply {
                        text = currency.oneDay.priceChangePct.toString()
                        setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_trending_up, 0, 0, 0)
                        setTextColor(homeViewModel.context.getColor(R.color.green))
                    }
                else
                    tvCoinGrowPercent.apply {
                        text = currency.oneDay.priceChangePct.toString()
                        setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_trending_down, 0, 0, 0)
                        setTextColor(homeViewModel.context.getColor(R.color.red_light))
                    }
                currency.logoUrl?.let { ivCoinIcon.loadSvg(it) }
                coinCard.setOnClickListener { onItemClick.invoke(currency) }
            }
        }
    }

}