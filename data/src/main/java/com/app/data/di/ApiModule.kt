package com.app.data.di

import com.app.data.api.ApiClient
import com.app.data.api.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {
    @Provides
    @Singleton
    fun provideApiInterface(): ApiInterface {
        return ApiClient.createApi()
    }
}