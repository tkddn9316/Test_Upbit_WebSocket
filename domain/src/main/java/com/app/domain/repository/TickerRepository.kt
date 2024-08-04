package com.app.domain.repository

import com.app.domain.model.Ticker
import kotlinx.coroutines.flow.Flow

interface TickerRepository {
    fun getTicker(): Flow<List<Ticker>>
}