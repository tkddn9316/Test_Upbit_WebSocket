package com.app.data.repository

import com.app.data.mapperToTicker
import com.app.data.source.MarketDataSource
import com.app.data.source.TickerDataSource
import com.app.domain.model.Ticker
import com.app.domain.repository.TickerRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TickerRepositoryImpl @Inject constructor(
    private val marketDataSource: MarketDataSource,
    private val tickerDataSource: TickerDataSource
) : TickerRepository {

    @OptIn(FlowPreview::class)
    override fun getTicker(): Flow<List<Ticker>> {
        return flow {
            marketDataSource.getMarket()
                .map {
                    // market code만 추출
                    it.map { list -> list.market }
                }
                .map {
                    // KRW만
                    it.filter { market -> market.contains("KRW") }
                }
                .map {
                    // 20개
                    if (it.size >= 20) it.subList(0, 19) else it
                }
                .flatMapConcat { marketList ->
                    // 주어진 Market Code를 이용하여 최초 현재가 구하기
                    tickerDataSource.getTicker(marketList)
                }
                .collect { emit(mapperToTicker(it)) }
        }
    }
}