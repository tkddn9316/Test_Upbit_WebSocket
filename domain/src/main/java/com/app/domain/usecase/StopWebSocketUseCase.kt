package com.app.domain.usecase

import com.app.domain.repository.WebSocketRepository
import javax.inject.Inject

class StopWebSocketUseCase @Inject constructor(private val webSocketManager: WebSocketRepository) {
    operator fun invoke() {
        webSocketManager.close()
    }
}