package com.prateekcode.cryypto.repo

import com.prateekcode.cryypto.api.CryptoApiRemoteData
import com.prateekcode.cryypto.api.CryptoCompareApiRemoteData
import com.prateekcode.cryypto.db.LocalData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(
    cryptoApiRemoteData: CryptoApiRemoteData,
    cryptoCompareApiRemoteData: CryptoCompareApiRemoteData,
    localData: LocalData
) {

    val cryptoRemoteData = cryptoApiRemoteData
    val cryptoCompareRemoteData = cryptoCompareApiRemoteData
    val favoriteCurrencies = localData

}