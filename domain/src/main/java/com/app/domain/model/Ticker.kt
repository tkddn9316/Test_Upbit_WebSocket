package com.app.domain.model

data class Ticker(
    val market: String,
    val code: String,   // market = code
    // 시가
    val opening_price: Double,
    // 고가
    val high_price: Double,
    // 저가
    val low_price: Double,
    // 종가(현재가)
    val trade_price: Double,
    // 전일 종가(UTC 0시 기준)
    val prev_closing_price: Double,
    // EVEN: 보합, RISE: 상승. FALL: 하락
    val change: String,
    // 매수/매도
    val ask_bid: String,
    // 24시간 누적 거래대금
    val acc_trade_price_24h: Double
)
