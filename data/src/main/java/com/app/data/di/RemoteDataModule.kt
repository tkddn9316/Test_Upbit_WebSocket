package com.app.data.di

import com.app.data.api.ApiInterface
import com.app.data.source.MarketDataSource
import com.app.data.source.MarketDataSourceImpl
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
}