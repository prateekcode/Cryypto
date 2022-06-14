package com.prateekcode.cryypto.api

import com.prateekcode.cryypto.BuildConfig
import com.prateekcode.cryypto.model.Currencies
import com.prateekcode.cryypto.utils.CURRENCY_ACTIVE_STATUS
import com.prateekcode.cryypto.utils.CURRENCY_PRICE_CONVERSION
import com.prateekcode.cryypto.utils.REQUEST_PAGE_SIZE
import com.prateekcode.cryypto.utils.STARTING_PAGE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApiService {

    @GET("currencies/ticker")
    suspend fun getCurrencies(
        @Query("key") apiKey: String = BuildConfig.API_KEY,
        @Query("status") status: String = CURRENCY_ACTIVE_STATUS,
        @Query("convert") convert: String = CURRENCY_PRICE_CONVERSION,
        @Query("page") page: Int = STARTING_PAGE,
        @Query("per-page") perPage: Int = REQUEST_PAGE_SIZE
    ): Response<List<Currencies>>

    @GET("currencies/ticker")
    suspend fun fetchCurrencyUsingId(
        @Query("key") apiKey: String = BuildConfig.API_KEY,
        @Query("ids") ids: String,
        @Query("convert") convert: String = CURRENCY_PRICE_CONVERSION
    ): Response<List<Currencies>>
}