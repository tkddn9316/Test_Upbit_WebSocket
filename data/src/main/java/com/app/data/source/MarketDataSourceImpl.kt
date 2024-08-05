package com.app.data.source

import com.app.data.api.ApiInterface
import com.app.data.model.MarketDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MarketDataSourceImpl @Inject constructor(private val apiInterface: ApiInterface) :
    MarketDataSource {

    override fun getMarket(): Flow<List<MarketDTO>> {
        return flow {
            emit(apiInterface.getMarket())
        }
    }
}