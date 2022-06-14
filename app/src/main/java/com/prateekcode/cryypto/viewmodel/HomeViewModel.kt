package com.prateekcode.cryypto.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.prateekcode.cryypto.api.CurrencyPaginationSource
import com.prateekcode.cryypto.repo.Repository
import com.prateekcode.cryypto.ui.fragment.FavoriteFragment
import com.prateekcode.cryypto.ui.fragment.HomeFragment
import com.prateekcode.cryypto.utils.CurrencyTrend
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    appContext: Application,
    private val repository: Repository
) : AndroidViewModel(appContext) {

    val context = appContext

    val homeFragment: HomeFragment by lazy {
        HomeFragment()
    }

    val favoriteFragment: FavoriteFragment by lazy {
        FavoriteFragment()
    }

    private fun getCryptoCurrencies(currencyTrend: CurrencyTrend) = Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { CurrencyPaginationSource(repository, currencyTrend) }).flow


    fun currencyList(currencyTrend: CurrencyTrend) =
        getCryptoCurrencies(currencyTrend).cachedIn(viewModelScope)
}