package com.prateekcode.cryypto.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(favorite: FavoriteEntity)

    @Delete
    suspend fun deleteCurrency(favorite: FavoriteEntity)

    @Query("DELETE FROM CRYYPTO_TABLE WHERE currencyId = :currencyId")
    suspend fun deleteCurrency(currencyId: String)

    @Query("SELECT * FROM CRYYPTO_TABLE")
    fun getAllCurrencies(): LiveData<List<FavoriteEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM CRYYPTO_TABLE WHERE currencyId = :currencyId)")
    fun isCurrencyExists(currencyId: String): Boolean

}