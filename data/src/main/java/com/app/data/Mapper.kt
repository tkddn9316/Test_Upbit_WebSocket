package com.app.data

import com.app.data.model.TickerDTO
import com.app.domain.model.Ticker

fun mapperToTicker(tickerDTO: TickerDTO): List<Ticker> =
    tickerDTO.tickers.toList().map {
        Ticker(
            it.market,
            it.code,
            it.opening_price,
            it.high_price,
            it.low_price,
            it.trade_price,
            it.prev_closing_price,
            it.change,
            it.ask_bid,
            it.acc_trade_price_24h
        )
    }