package com.app.data.model

import com.google.gson.annotations.SerializedName

data class TickerDTO(
    // KRW-BTC
    @SerializedName("market")
    val market: String,
    // 시가
    @SerializedName("opening_price")
    val opening_price: Double,
    // 고가
    @SerializedName("high_price")
    val high_price: Double,
    // 저가
    @SerializedName("low_price")
    val low_price: Double,
    // 종가(현재가)
    @SerializedName("trade_price")
    val trade_price: Double,
    // 전일 종가(UTC 0시 기준)
    @SerializedName("prev_closing_price")
    val prev_closing_price: Double,
    // EVEN: 보합, RISE: 상승. FALL: 하락
    @SerializedName("change")
    val change: String,
    // 24시간 누적 거래대금
    @SerializedName("acc_trade_price_24h")
    val acc_trade_price_24h: Double
)
