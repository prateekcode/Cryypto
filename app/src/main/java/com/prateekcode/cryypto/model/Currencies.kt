package com.prateekcode.cryypto.model


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.prateekcode.cryypto.utils.CRYYPTO_TABLE
import kotlinx.parcelize.Parcelize

@Parcelize
data class Currencies(
    @SerializedName("circulating_supply")
    val circulatingSupply: String?, // 17758462
    @SerializedName("currency")
    val currency: String?, // BTC
    @SerializedName("1d")
    val oneDay: CurrencyChange?,
    @SerializedName("7d")
    val sevenDay: CurrencyChange?,
    @SerializedName("30d")
    val thirtyDay: CurrencyChange?,
    @SerializedName("365d")
    val oneYear: CurrencyChange?,
    @SerializedName("first_candle")
    val firstCandle: String?, // 2011-08-18T00:00:00Z
    @SerializedName("first_order_book")
    val firstOrderBook: String?, // 2017-01-06T00:00:00Z
    @SerializedName("first_priced_at")
    val firstPricedAt: String?, // 2017-08-18T18:22:19Z
    @SerializedName("first_trade")
    val firstTrade: String?, // 2011-08-18T00:00:00Z
    @SerializedName("high")
    val high: String?, // 19404.81116899
    @SerializedName("high_timestamp")
    val highTimestamp: String?, // 2017-12-16
    @PrimaryKey @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: String?, // BTC
    @SerializedName("logo_url")
    val logoUrl: String?, // https://s3.us-east-2.amazonaws.com/nomics-api/static/images/currencies/btc.svg
    @SerializedName("market_cap")
    val marketCap: String?, // 150083247116.70
    @SerializedName("market_cap_dominance")
    val marketCapDominance: String?, // 0.4080
    @SerializedName("max_supply")
    val maxSupply: String?, // 21000000
    @SerializedName("name")
    val name: String?, // Bitcoin
    @SerializedName("num_exchanges")
    val numExchanges: String?, // 357
    @SerializedName("num_pairs")
    val numPairs: String?, // 42118
    @SerializedName("num_pairs_unmapped")
    val numPairsUnmapped: String?, // 4591
    @SerializedName("price")
    val price: Double, // 8451.36516421
    @SerializedName("price_date")
    val priceDate: String?, // 2019-06-14T00:00:00Z
    @SerializedName("price_timestamp")
    val priceTimestamp: String?, // 2019-06-14T12:35:00Z
    @SerializedName("rank")
    val rank: Int, // 1
    @SerializedName("rank_delta")
    val rankDelta: String?, // 0
    @SerializedName("status")
    val status: String?, // active
    @SerializedName("symbol")
    val symbol: String?, // BTC
    @SerializedName("transparent_market_cap")
    val transparentMarketCap: String? // 150003247116.70
) : Parcelable

@Parcelize
data class CurrencyChange(
    @SerializedName("market_cap_change")
    val marketCapChange: Double, // 4805518049.63
    @SerializedName("market_cap_change_pct")
    val marketCapChangePct: Double, // 0.03
    @SerializedName("price_change")
    val priceChange: Double, // 269.75208019
    @SerializedName("price_change_pct")
    val priceChangePct: Double, // 0.03297053
    @SerializedName("transparent_market_cap_change")
    val transparentMarketCapChange: Double, // 4800518049.00
    @SerializedName("transparent_market_cap_change_pct")
    val transparentMarketCapChangePct: Double, // 0.0430
    @SerializedName("volume")
    val volume: Double, // 1110989572.04
    @SerializedName("volume_change")
    val volumeChange: Double, // -24130098.49
    @SerializedName("volume_change_pct")
    val volumeChangePct: Double, // -0.02
) : Parcelable
