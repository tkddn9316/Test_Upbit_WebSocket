package com.app.domain.usecase

import com.app.domain.repository.WebSocketRepository
import javax.inject.Inject

class SendWebSocketUseCase @Inject constructor(private val webSocketManager: WebSocketRepository) {
    operator fun invoke(message: String) {
        webSocketManager.send(message)
    }
}