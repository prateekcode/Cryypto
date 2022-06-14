package com.prateekcode.cryypto.db

import javax.inject.Inject

class LocalData @Inject constructor(private val favoriteDao: FavoriteDao) {

    suspend fun insertFavorite(favorite: FavoriteEntity) = favoriteDao.insertCurrency(favorite)
    suspend fun deleteCurrency(favorite: FavoriteEntity) = favoriteDao.deleteCurrency(favorite)
    suspend fun deleteCurrency(currencyId: String) = favoriteDao.deleteCurrency(currencyId)
    fun getListCurrencies() = favoriteDao.getAllCurrencies()
    fun isCurrencyExists(currencyId: String) = favoriteDao.isCurrencyExists(currencyId)
}