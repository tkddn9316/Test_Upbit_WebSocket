package com.app.test_upbit_websocket.util

import android.util.Log
import com.app.domain.model.Ticker
import com.app.domain.model.Ticket
import com.app.domain.model.Type
import com.app.domain.usecase.SendWebSocketUseCase
import com.app.domain.usecase.StartWebSocketUseCase
import com.app.domain.usecase.StopWebSocketUseCase
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import javax.inject.Inject

class WebSocketManager @Inject constructor(
    private val startWebSocketUseCase: StartWebSocketUseCase,
    private val sendWebSocketUseCase: SendWebSocketUseCase,
    private val stopWebSocketUseCase: StopWebSocketUseCase,
    private val gson: Gson
) {
    private val _webSocketMessages = MutableStateFlow<Ticker?>(null)
    val webSocketMessages: StateFlow<Ticker?> = _webSocketMessages

    private val marketCodeList = mutableListOf<String>()

    private val orderbookListener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)

            // 보낼 메시지 세팅하기
            val ticket = Ticket("ticker")
            val type = Type("trade", marketCodeList)
            val gson = gson.toJson(arrayListOf(ticket, type))
            sendWebSocketUseCase(gson)
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            super.onMessage(webSocket, bytes)
            // 메시지 받음
            val bytesToString = bytes.toByteArray().decodeToString()
            val ticker = gson.fromJson(bytesToString, Ticker::class.java)

            _webSocketMessages.value = ticker
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            Log.e("WebSocketManager", t.message ?: "")
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
        }
    }

    fun start(marketCodes: List<String>) {
        marketCodeList.clear()
        marketCodeList.addAll(marketCodes)
        startWebSocketUseCase(orderbookListener)
    }

    fun stop() {
        stopWebSocketUseCase()
    }
}