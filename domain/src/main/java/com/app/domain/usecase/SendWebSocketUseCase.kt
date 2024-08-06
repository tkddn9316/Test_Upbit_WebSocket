package com.app.domain.usecase

import com.app.domain.repository.WebSocketRepository
import okhttp3.WebSocketListener
import javax.inject.Inject

class SendWebSocketUseCase @Inject constructor(private val webSocketManager: WebSocketRepository) {
    operator fun invoke(message: String) {
        webSocketManager.send(message)
    }
}