package com.prateekcode.cryypto.api

import com.prateekcode.cryypto.model.HistoricalResponse
import com.prateekcode.cryypto.utils.CURRENCY_PRICE_CONVERSION
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoCompareApiService {



    @GET("histoday")
    suspend fun getHistoricalDailyData(
        @Query("fsym") requiredCurrency: String,
        @Query("limit") requiredTime: String,
        @Query("tsym") convertTo: String = CURRENCY_PRICE_CONVERSION
    ): Response<HistoricalResponse>

    @GET("histohour")
    suspend fun getHistoricalHourlyData(
        @Query("fsym") requiredCurrency: String,
        @Query("limit") requiredTime: String = "24",
        @Query("tsym") convertTo: String = CURRENCY_PRICE_CONVERSION
    ): Response<HistoricalResponse>

}