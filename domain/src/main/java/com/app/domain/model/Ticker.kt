package com.app.domain.model

import android.content.Context
import android.graphics.Color
import com.app.domain.R

data class Ticker(
    val market: String,
    var code: String = "",   // market = code
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
    var ask_bid: String = "",
    // 24시간 누적 거래대금
    val acc_trade_price_24h: Double
) {

    // 거래대금 text
    fun setAcc(): String {
        return acc_trade_price_24h.toString()
    }

    // 현재가 text
    fun setPrice(): String {
        return trade_price.toString()
    }

    // 색 지정
//    fun setColor(): Int {
//        return when(change) {
//            FConstrant.PriceColor.RISE.name -> Color.BLACK
//            FConstrant.PriceColor.FALL.name -> context.getColor(R.color.blue_500)
//            else -> context.getColor(R.color.black_700)
//        }
//    }
}
