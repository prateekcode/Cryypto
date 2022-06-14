package com.prateekcode.cryypto.api

import javax.inject.Inject

class CryptoApiRemoteData @Inject constructor(private val cryptoApiService: CryptoApiService) {

    suspend fun getCurrencyList(pageNum: Int) =
        cryptoApiService.getCurrencies(page = pageNum)

    suspend fun getCurrenciesIds(ids: String) =
        cryptoApiService.fetchCurrencyUsingId(ids = ids)

}