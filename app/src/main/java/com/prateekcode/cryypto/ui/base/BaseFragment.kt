package com.prateekcode.cryypto.ui.base

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.prateekcode.cryypto.adapter.CurrencyAdapter
import com.prateekcode.cryypto.ui.activities.CoinDetailActivity
import com.prateekcode.cryypto.ui.activities.MainActivity
import com.prateekcode.cryypto.utils.PARAM_DATA
import com.prateekcode.cryypto.utils.launchActivity
import com.prateekcode.cryypto.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    val homeVm by viewModels<HomeViewModel>()

    lateinit var parentActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        parentActivity = requireActivity() as MainActivity
    }

    val currencyAdapter: CurrencyAdapter by lazy {
        CurrencyAdapter(homeVm) { currency ->
            parentActivity.launchActivity<CoinDetailActivity> {
                putExtra(PARAM_DATA, currency)
            }
        }
    }

}