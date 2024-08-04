package com.app.data.source

import com.app.data.model.MarketDTO
import kotlinx.coroutines.flow.Flow

interface MarketDataSource {
    fun getMarket(): Flow<MarketDTO>
}