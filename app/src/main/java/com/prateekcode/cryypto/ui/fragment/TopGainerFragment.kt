package com.prateekcode.cryypto.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.prateekcode.cryypto.databinding.FragmentTopGainerBinding
import com.prateekcode.cryypto.ui.base.BaseFragment
import com.prateekcode.cryypto.utils.CurrencyTrend
import com.prateekcode.cryypto.utils.hide
import com.prateekcode.cryypto.utils.init
import com.prateekcode.cryypto.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopGainerFragment : BaseFragment() {

    private lateinit var mContext: Context

    private val binding: FragmentTopGainerBinding by lazy {
        FragmentTopGainerBinding.inflate(layoutInflater)
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
        initCurrencies()
        initShimmerEffect()
        binding.gainerCoins.rvCoinList.apply {
            init(mContext)
            adapter = currencyAdapter
        }
        binding.errorLayout.btnRetry.setOnClickListener {
            initCurrencies()
        }
    }

    private fun initCurrencies() {
        lifecycleScope.launch {
            homeVm.currencyList(CurrencyTrend.GAINER).collectLatest {
                currencyAdapter.submitData(it)
            }
        }
    }

    private fun initShimmerEffect() {
        currencyAdapter.addLoadStateListener { loadState ->
            when {
                loadState.refresh is LoadState.Loading -> {
                    binding.apply {
                        shimmerContainer.show()
                        shimmerContainer.startShimmer()
                        gainerCoins.rvCoinList.hide()
                        errorLayout.root.hide()
                    }
                }
                loadState.append is LoadState.Loading -> {
                    binding.apply {
                        shimmerContainer.hide()
                        shimmerContainer.stopShimmer()
                        gainerCoins.rvCoinList.show()
                        errorLayout.root.hide()
                    }
                }
                loadState.refresh is LoadState.Error -> {
                    binding.apply {
                        shimmerContainer.hide()
                        shimmerContainer.stopShimmer()
                        gainerCoins.rvCoinList.hide()
                        errorLayout.root.show()
                    }
                }
            }
        }
    }
}