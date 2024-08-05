package com.app.data.di

import com.app.data.api.ApiInterface
import com.app.data.source.MarketDataSource
import com.app.data.source.MarketDataSourceImpl
import com.app.data.source.TickerDataSource
import com.app.data.source.TickerDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {
    @Provides
    @Singleton
    fun provideMarketDataSource(apiInterface: ApiInterface): MarketDataSource {
        return MarketDataSourceImpl(apiInterface)
    }

    @Provides
    @Singleton
    fun provideTickerDataSource(apiInterface: ApiInterface): TickerDataSource {
        return TickerDataSourceImpl(apiInterface)
    }
}