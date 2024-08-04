package com.app.data.source

import com.app.data.api.ApiInterface
import com.app.data.model.MarketDTO
import com.app.data.model.TickerDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TickerDataSourceImpl @Inject constructor(private val apiInterface: ApiInterface): TickerDataSource {
    override fun getTicker(markets: List<String>): Flow<TickerDTO> {
        return flow {
            emit(
                apiInterface.getTickers(markets)
            )
        }
    }
}