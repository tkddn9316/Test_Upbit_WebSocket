package com.app.domain.usecase

import com.app.domain.repository.WebSocketRepository
import okhttp3.WebSocketListener
import javax.inject.Inject

class StartWebSocketUseCase @Inject constructor(private val webSocketManager: WebSocketRepository) {
    operator fun invoke(orderBookListener: WebSocketListener) {
        webSocketManager.connect(orderBookListener)
    }
}