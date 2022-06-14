package com.prateekcode.cryypto.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.prateekcode.cryypto.model.Currencies
import com.prateekcode.cryypto.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    appContext: Application,
    private val repository: Repository
) : AndroidViewModel(appContext) {

    fun getFavoriteList() = repository.favoriteCurrencies.getListCurrencies()

    private val _fetchCurrencyMutableList: MutableLiveData<List<Currencies>> = MutableLiveData()
    val fetchCurrencyMutableList get() = _fetchCurrencyMutableList

    fun fetchCurrencies(currencyId: String) {
        viewModelScope.launch {
            val currencyResponse = repository.cryptoRemoteData.getCurrenciesIds(currencyId)
            _fetchCurrencyMutableList.postValue(currencyResponse.body())
        }
    }
}