package com.prateekcode.cryypto.api

import android.content.Context
import com.prateekcode.cryypto.BuildConfig
import com.prateekcode.cryypto.utils.API_TIMEOUT_DELAY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.requestly.rqinterceptor.api.RQCollector
import io.requestly.rqinterceptor.api.RQInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideCollector(@ApplicationContext applicationContext: Context): RQCollector {
        return RQCollector(context = applicationContext, sdkKey = BuildConfig.REQUESTLY_SDK_KEY)
    }

    @Singleton
    @Provides
    fun provideRqInterceptor(
        @ApplicationContext applicationContext: Context,
        rqCollector: RQCollector
    ): RQInterceptor {
        return RQInterceptor.Builder(applicationContext).collector(rqCollector).build()
    }

    @Singleton
    @Provides
    fun provideHttpClient(rqInterceptor: RQInterceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(rqInterceptor)
            .readTimeout(API_TIMEOUT_DELAY, TimeUnit.SECONDS)
            .connectTimeout(API_TIMEOUT_DELAY, TimeUnit.SECONDS)
            .writeTimeout(API_TIMEOUT_DELAY, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Singleton
    @Provides
    @Named("Nomic")
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.NOMIC_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    @Named("CryptoCompare")
    fun provideRetrofitCryptoCompare(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.CRYPTO_COMPARE_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideCryptoApiService(@Named("Nomic") retrofit: Retrofit): CryptoApiService =
        retrofit.create(CryptoApiService::class.java)

    @Singleton
    @Provides
    fun provideCryptoCompareApiService(@Named("CryptoCompare") retrofit: Retrofit): CryptoCompareApiService =
        retrofit.create(CryptoCompareApiService::class.java)



}