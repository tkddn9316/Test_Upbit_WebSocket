package com.app.domain.usecase

import com.app.domain.model.Ticker
import com.app.domain.util.Util
import javax.inject.Inject

class SortTickersByAccUseCase @Inject constructor() {
    operator fun invoke(tickers: List<Ticker>, sortOption: Int): List<Ticker> {
        return when (sortOption) {
            Util.SortOption.Descending.value -> tickers.sortedByDescending { it.acc_trade_price_24h }
            Util.SortOption.Ascending.value -> tickers.sortedBy { it.acc_trade_price_24h }
            else -> tickers
        }
    }
}