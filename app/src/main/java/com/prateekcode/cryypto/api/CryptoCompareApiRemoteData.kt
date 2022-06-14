package com.prateekcode.cryypto.api

import javax.inject.Inject

class CryptoCompareApiRemoteData @Inject constructor(
    private val cryptoCompareApiService: CryptoCompareApiService
) {

    suspend fun getHistoricalDataDaily(requiredCurrency: String, requiredTime: String) =
        cryptoCompareApiService.getHistoricalDailyData(requiredCurrency, requiredTime)

    suspend fun getHistoricalHourlyData(requiredCurrency: String) =
        cryptoCompareApiService.getHistoricalHourlyData(requiredCurrency)
}