package com.prateekcode.cryypto.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.chip.Chip
import com.prateekcode.cryypto.R
import com.prateekcode.cryypto.databinding.ActivityCoinDetailBinding
import com.prateekcode.cryypto.model.Currencies
import com.prateekcode.cryypto.ui.fragment.ChartFragment
import com.prateekcode.cryypto.utils.*
import com.prateekcode.cryypto.viewmodel.CoinDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoinDetailActivity : AppCompatActivity() {

    private val binding: ActivityCoinDetailBinding by lazy {
        ActivityCoinDetailBinding.inflate(layoutInflater)
    }

    private val detailVm by viewModels<CoinDetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        handleExtras()
        backIconPress()
    }

    private fun handleExtras() {
        intent?.getParcelableExtra<Currencies>(PARAM_DATA)?.let {
            selectedChip(it)

            binding.apply {
                it.id?.let { it1 -> insertFavoriteCurrency(it1) }
                tvCoinTitle.text = it.id
                layoutCoinDetail.apply {
                    it.logoUrl?.let { it1 -> ivCoinIcon.loadSvg(it1) }
                    tvAboutCoin.text = getString(R.string.about_coin, it.name)

                    tvCurrentBuyPrice.text =
                        getString(R.string.price_placeholder, it.price.roundOffDecimal().toString())
                    if (it.oneDay!!.priceChangePct >= 0)
                        tvCoinMarketPercentage.apply {
                            text = it.oneDay.priceChangePct.toString()
                            setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.ic_trending_up,
                                0,
                                0,
                                0
                            )
                            setTextColor(getColor(R.color.green))
                        }
                    else
                        tvCoinMarketPercentage.apply {
                            text = it.oneDay!!.priceChangePct.toString()
                            setCompoundDrawablesWithIntrinsicBounds(
                                R.drawable.ic_trending_down,
                                0,
                                0,
                                0
                            )
                            setTextColor(getColor(R.color.red_light))
                        }

                    marketRank.apply {
                        tvMarketRank.text = getString(R.string.market_rank)
                        tvMarketRankValue.text =
                            getString(R.string.coin_rank_placeholder, it.rank.toString())
                    }

                    marketCap.apply {
                        tvMarketRank.text = getString(R.string.market_cap)
                        tvMarketRankValue.text = it.marketCap.toString()
                    }

                    coinStatus.apply {
                        tvMarketRank.text = getString(R.string.coin_status)
                        tvMarketRankValue.text = it.status
                    }

                    circulatingSupply.apply {
                        tvMarketRank.text = getString(R.string.circulating_supply)
                        tvMarketRankValue.text = it.circulatingSupply
                    }

                    maximumSupply.apply {
                        tvMarketRank.text = getString(R.string.maximum_supply)
                        tvMarketRankValue.text = it.maxSupply
                    }

                    priceChange.apply {
                        tvMarketRank.text = getString(R.string.price_change)
                        tvMarketRankValue.text = it.price.toString()
                    }

                    volumeChange.apply {
                        tvMarketRank.text = getString(R.string.volume_change)
                        tvMarketRankValue.text = it.firstCandle
                    }

                }
            }
        }
    }

    private fun showChartFragment(reqCurrency: String, reqTime: String, isChange: Boolean) {
        val chartFragment = ChartFragment()
        val bundle = Bundle().apply {
            putString(PARAM_REQUIRED_CURRENCY, reqCurrency)
            putString(PARAM_REQUIRED_TIME, reqTime)
            putBoolean(PARAM_IS_CHANGE_POSITIVE, isChange)
        }
        chartFragment.arguments = bundle
        replaceFragmentInActivity(chartFragment, R.id.vp_chart_container)
    }

    private fun backIconPress() {
        binding.ivBackBtn.setOnClickListener {
            onBackPressed()
        }
    }

    private fun selectedChip(currency: Currencies) {
        showChartFragment(currency.currency.toString(), "1", currency.oneDay!!.priceChangePct >= 0)
        binding.layoutCoinDetail.timelineTimeSelector.setOnCheckedStateChangeListener { chipGroup, checkedIds ->
            checkedIds.forEach {
                when (chipGroup.findViewById<Chip>(it).text) {
                    getString(R.string.one_day) -> {
                        showChartFragment(
                            currency.currency.toString(),
                            "1",
                            currency.oneDay.priceChangePct >= 0
                        )
                    }
                    getString(R.string.one_week) -> {
                        showChartFragment(
                            currency.currency.toString(),
                            "7",
                            currency.sevenDay!!.priceChangePct >= 0
                        )
                    }
                    getString(R.string.one_month) -> {
                        showChartFragment(
                            currency.currency.toString(),
                            "30",
                            currency.thirtyDay!!.priceChangePct >= 0
                        )
                    }
                    getString(R.string.one_year) -> {
                        showChartFragment(
                            currency.currency.toString(),
                            "365",
                            currency.oneYear!!.priceChangePct >= 0
                        )
                    }
                }
            }
        }
    }

    private fun insertFavoriteCurrency(currencyId: String) {
        binding.ivFavoriteBtn.setOnClickListener {
            detailVm.insertFavoriteCurrency(currencyId)
            showToast(getString(R.string.coin_saved))
            binding.ivFavoriteBtn.setImageDrawable(
                AppCompatResources.getDrawable(
                    this,
                    R.drawable.ic_favorite
                )
            )
        }
        if (detailVm.isCurrencyExists(currencyId)) {
            binding.ivFavoriteBtn.setImageDrawable(
                AppCompatResources.getDrawable(
                    this,
                    R.drawable.ic_favorite
                )
            )
            binding.ivFavoriteBtn.setOnClickListener {
                detailVm.deleteFavoriteCurrency(currencyId)
                showToast(getString(R.string.coin_removed))
                binding.ivFavoriteBtn.setImageDrawable(
                    AppCompatResources.getDrawable(
                        this,
                        R.drawable.ic_favorite_border
                    )
                )
            }
        } else
            binding.ivFavoriteBtn.setImageDrawable(
                AppCompatResources.getDrawable(
                    this,
                    R.drawable.ic_favorite_border
                )
            )

    }

}