package com.app.domain.repository

import okhttp3.WebSocketListener

interface WebSocketRepository {
    fun connect(orderBookListener: WebSocketListener)
    fun send(message: String)
    fun close()
}