package com.app.data.api

import com.app.data.model.MarketDTO
import com.app.data.model.TickerDTO
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 서버와 통신 할 API 리스트
 */
interface ApiInterface {
    @GET("v1/market/all")
    suspend fun getMarket(): List<MarketDTO>

    @GET("v1/ticker")
    suspend fun getTickers(
        @Query("markets") markets: List<String>
    ): List<TickerDTO>
}