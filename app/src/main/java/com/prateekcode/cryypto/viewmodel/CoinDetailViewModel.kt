package com.prateekcode.cryypto.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.prateekcode.cryypto.db.FavoriteEntity
import com.prateekcode.cryypto.model.Data
import com.prateekcode.cryypto.repo.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    appContext: Application,
    private val repository: Repository
) : AndroidViewModel(appContext) {

    private val _historicalHourlyMutableLiveData: MutableLiveData<Data> = MutableLiveData()
    val historicalHourlyData get() = _historicalHourlyMutableLiveData

    fun getCurrencyHistoricalHourlyData(requiredCurrency: String) {
        viewModelScope.launch {
            val hourlyData =
                repository.cryptoCompareRemoteData.getHistoricalHourlyData(requiredCurrency)
                    .body()!!.data
            _historicalHourlyMutableLiveData.postValue(hourlyData)
        }
    }

    private val _historicalDailyMutableLiveData: MutableLiveData<Data> = MutableLiveData()
    val historicalDailyData get() = _historicalDailyMutableLiveData

    fun getCurrencyHistoryDailyData(requiredCurrency: String, requiredTime: String) {
        viewModelScope.launch {
            val dailyData = repository.cryptoCompareRemoteData.getHistoricalDataDaily(
                requiredCurrency,
                requiredTime
            )
                .body()!!.data
            _historicalDailyMutableLiveData.postValue(dailyData)
        }
    }


    fun insertFavoriteCurrency(currencyId: String) {
        viewModelScope.launch {
            repository.favoriteCurrencies.insertFavorite(FavoriteEntity(currencyId = currencyId))
        }
    }

    fun deleteFavoriteCurrency(currencyId: String) {
        viewModelScope.launch {
            repository.favoriteCurrencies.deleteCurrency(currencyId)
        }
    }

    fun isCurrencyExists(currencyId: String) =
        repository.favoriteCurrencies.isCurrencyExists(currencyId)
}