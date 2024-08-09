package com.app.test_upbit_websocket.di

import com.app.domain.usecase.SendWebSocketUseCase
import com.app.domain.usecase.StartWebSocketUseCase
import com.app.domain.usecase.StopWebSocketUseCase
import com.app.test_upbit_websocket.util.WebSocketManager
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class WebSocketModule {
    @Provides
    @Singleton
    fun provideGson(): Gson {
        // Dagger-Hilt는 Gson과 같은 객체를 기본적으로 제공하지 않기 때문에, 직접 제공해야 합니다.
        return Gson()
    }

    @Provides
    @Singleton
    fun provideWebSocketManager(
        startWebSocketUseCase: StartWebSocketUseCase,
        sendWebSocketUseCase: SendWebSocketUseCase,
        stopWebSocketUseCase: StopWebSocketUseCase,
        gson: Gson
    ): WebSocketManager {
        return WebSocketManager(
            startWebSocketUseCase,
            sendWebSocketUseCase,
            stopWebSocketUseCase,
            gson
        )
    }
}