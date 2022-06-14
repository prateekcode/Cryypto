package com.prateekcode.cryypto.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.prateekcode.cryypto.R
import com.prateekcode.cryypto.adapter.diffutils.FavoriteCurrencyDiffUtil
import com.prateekcode.cryypto.databinding.ItemCryptoBinding
import com.prateekcode.cryypto.model.Currencies
import com.prateekcode.cryypto.utils.loadSvg
import com.prateekcode.cryypto.utils.roundOffDecimal

class FavoriteCurrencyAdapter(private val onItemClick: (Currencies) -> Unit) : BaseAdapter() {

    inner class CurrencyViewHolder(private val binding: ItemCryptoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currency: Currencies) {
            binding.apply {
                tvCoinName.text = currency.name
                tvCoinId.text = currency.id
                tvCoinCurrentPrice.text = binding.root.context.getString(
                    R.string.price_placeholder,
                    currency.price.roundOffDecimal().toString()
                )
                if (currency.oneDay?.priceChangePct!! >= 0)
                    tvCoinGrowPercent.apply {
                        text = currency.oneDay.priceChangePct.toString()
                        setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_trending_up, 0, 0, 0)
                        setTextColor(binding.root.context.getColor(R.color.green))
                    }
                else
                    tvCoinGrowPercent.apply {
                        text = currency.oneDay.priceChangePct.toString()
                        setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_trending_down,
                            0,
                            0,
                            0
                        )
                        setTextColor(binding.root.context.getColor(R.color.red_light))
                    }
                currency.logoUrl?.let { ivCoinIcon.loadSvg(it) }
                coinCard.setOnClickListener { onItemClick.invoke(currency) }
            }
        }
    }

    override fun getListItemCount() = currencyList.size

    override fun getViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemCryptoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun bindHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = currencyList[position]
        (holder as CurrencyViewHolder).bind(currentItem)
    }

    private val asyncListDiffer = AsyncListDiffer(this, FavoriteCurrencyDiffUtil())
    private val currencyList: List<Currencies> get() = asyncListDiffer.currentList

    fun setCurrencies(writerList: List<Currencies>) {
        asyncListDiffer.submitList(writerList)
    }


}