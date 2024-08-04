package com.app.data.di

import com.app.data.repository.TickerRepositoryImpl
import com.app.data.source.MarketDataSource
import com.app.data.source.TickerDataSource
import com.app.domain.repository.TickerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt에게 해당 Repository를 제공
 */
@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideMarketRepository(
        marketDataSource: MarketDataSource,
        tickerDataSource: TickerDataSource
    ): TickerRepository {
        return TickerRepositoryImpl(marketDataSource, tickerDataSource)
    }
}