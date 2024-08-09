package com.app.domain.usecase

import com.app.domain.model.Ticker
import com.app.domain.util.Util
import javax.inject.Inject

class SortTickersByPriceUseCase @Inject constructor() {
    operator fun invoke(tickers: List<Ticker>, sortOption: Int): List<Ticker> {
        return when (sortOption) {
            Util.SortOption.Descending.value -> tickers.sortedByDescending { it.trade_price }
            Util.SortOption.Ascending.value -> tickers.sortedBy { it.trade_price }
            else -> tickers
        }
    }
}