package com.app.data.repository

import com.app.data.Util
import com.app.domain.repository.WebSocketRepository
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

/**
 * 웹소켓 연결 repository 구현부
 */
class WebSocketRepositoryImpl @Inject constructor(private val okHttpClient: OkHttpClient): WebSocketRepository {
    private var socket: WebSocket? = null

    override fun connect(orderBookListener: WebSocketListener) {
        val request = Request.Builder()
            .url(Util.WSS_URL)
            .build()

        socket = okHttpClient.newWebSocket(request, orderBookListener)
    }

    override fun send(message: String) {
        socket?.send(message)
    }

    override fun close() {
        socket?.close(1000, "close")
        socket = null
    }
}