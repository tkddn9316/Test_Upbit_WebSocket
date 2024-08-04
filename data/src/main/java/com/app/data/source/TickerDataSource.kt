package com.app.data.source

import com.app.data.model.MarketDTO
import com.app.data.model.TickerDTO
import kotlinx.coroutines.flow.Flow

interface TickerDataSource {
    fun getTicker(
        markets: List<String>
    ): Flow<TickerDTO>
}