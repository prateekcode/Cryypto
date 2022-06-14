package com.prateekcode.cryypto.model

import com.google.gson.annotations.SerializedName

data class HistoricalResponse(
    @SerializedName("Data")
    val `data`: Data?,
    @SerializedName("HasWarning")
    val hasWarning: Boolean?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Response")
    val response: String?,
    @SerializedName("Type")
    val type: Int?
)

data class Data(
    @SerializedName("Aggregated")
    val aggregated: Boolean?,
    @SerializedName( "Data")
    val `data`: List<HistoricalData>?,
    @SerializedName( "TimeFrom")
    val timeFrom: Int?,
    @SerializedName( "TimeTo")
    val timeTo: Int?
)

data class HistoricalData(
    @SerializedName("close")
    val close: Double?,
    @SerializedName("conversionSymbol")
    val conversionSymbol: String?,
    @SerializedName("conversionType")
    val conversionType: String?,
    @SerializedName("high")
    val high: Double?,
    @SerializedName("low")
    val low: Double?,
    @SerializedName("open")
    val `open`: Double?,
    @SerializedName("time")
    val time: Int?,
    @SerializedName("volumefrom")
    val volumeFrom: Double?,
    @SerializedName("volumeto")
    val volumeTo: Double?
)