package com.app.data

import com.app.data.model.TickerDTO
import com.app.domain.model.Ticker

fun mapperToTicker(tickerDTO: List<TickerDTO>): List<Ticker> =
    tickerDTO.toList().map {
        Ticker(
            it.market,
            "",
            it.opening_price,
            it.high_price,
            it.low_price,
            it.trade_price,
            it.prev_closing_price,
            it.change,
            "",
            it.acc_trade_price_24h
        )
    }