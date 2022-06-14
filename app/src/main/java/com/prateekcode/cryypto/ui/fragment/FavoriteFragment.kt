package com.prateekcode.cryypto.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.prateekcode.cryypto.adapter.FavoriteCurrencyAdapter
import com.prateekcode.cryypto.databinding.FragmentFavoriteBinding
import com.prateekcode.cryypto.ui.activities.CoinDetailActivity
import com.prateekcode.cryypto.ui.base.BaseFragment
import com.prateekcode.cryypto.utils.*
import com.prateekcode.cryypto.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment() {

    private lateinit var mContext: Context

    private val binding: FragmentFavoriteBinding by lazy {
        FragmentFavoriteBinding.inflate(layoutInflater)
    }

    private val favoriteVm by viewModels<FavoriteViewModel>()

    private val favoriteAdapter: FavoriteCurrencyAdapter by lazy {
        FavoriteCurrencyAdapter {
            parentActivity.launchActivity<CoinDetailActivity> {
                putExtra(PARAM_DATA, it)
            }
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerInternet()
    }

    private fun initFavoriteList() {
        showLoading()
        favoriteVm.getFavoriteList().observe(viewLifecycleOwner) { currencyList ->
            var currenciesId = ""
            currencyList.forEach {
                if (currenciesId.isEmpty())
                    currenciesId = it.currencyId
                else
                    currenciesId += ", ${it.currencyId}"
            }
            if (currenciesId.isNotEmpty()) {
                binding.lottieNoContent.hide()
                favoriteVm.fetchCurrencies(currenciesId)
            } else {
                hideLoading()
                binding.lottieNoContent.show()
            }

        }

        favoriteVm.fetchCurrencyMutableList.observe(viewLifecycleOwner) {
            it?.let { currencyList ->
                hideLoading()
                favoriteAdapter.setCurrencies(currencyList)
            } ?: showErrorLayout()

        }
    }

    private fun initView() {
        binding.favoriteCoins.rvCoinList.init(mContext, favoriteAdapter)
    }

    private fun showLoading() {
        binding.apply {
            shimmerContainer.show()
            shimmerContainer.startShimmer()
            favoriteCoins.rvCoinList.hide()
            errorLayout.root.hide()
        }
    }

    private fun hideLoading() {
        binding.apply {
            shimmerContainer.hide()
            shimmerContainer.stopShimmer()
            favoriteCoins.rvCoinList.show()
            errorLayout.root.hide()
        }
    }

    private fun showErrorLayout() {
        binding.apply {
            shimmerContainer.hide()
            shimmerContainer.stopShimmer()
        }.errorLayout.apply {
            root.show()
            btnRetry.setOnClickListener {
                initFavoriteList()
            }
        }
    }

    private fun observerInternet() {
        NetworkConnectionUtil.getNetworkLiveData(mContext)
            .observe(viewLifecycleOwner) { isInternetAvailable ->
                if (isInternetAvailable) {
                    binding.apply {
                        tvNoInternetText.hide()
                        lottieNoInternet.hide()
                        favoriteCoins.rvCoinList.show()
                    }
                    initFavoriteList()
                    initView()
                } else
                    binding.apply {
                        lottieNoInternet.show()
                        tvNoInternetText.show()
                        favoriteCoins.rvCoinList.hide()
                        shimmerContainer.hide()
                    }
            }
    }

}