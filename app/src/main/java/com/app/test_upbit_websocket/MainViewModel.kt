package com.app.test_upbit_websocket

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.domain.model.Ticker
import com.app.domain.usecase.GetTickerUseCase
import com.app.domain.usecase.SendWebSocketUseCase
import com.app.domain.usecase.StartWebSocketUseCase
import com.app.domain.usecase.StopWebSocketUseCase
import com.app.test_upbit_websocket.Util.SortOption
import com.app.test_upbit_websocket.base.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTickerUseCase: GetTickerUseCase,
    private val startWebSocketUseCase: StartWebSocketUseCase,
    private val sendWebSocketUseCase: SendWebSocketUseCase,
    private val stopWebSocketUseCase: StopWebSocketUseCase,
) : BaseViewModel() {

    private val _tickerListResult = MutableStateFlow<List<Ticker>>(emptyList())
    val tickerListResult = _tickerListResult.asStateFlow()
    private val marketCodeList = mutableListOf<String>()
    private val gson = Gson()

    private val _sortByPrice = MutableLiveData(SortOption.Nothing.value)    // 현재가 정렬
    val sortByPrice: LiveData<Int> get() = _sortByPrice
    private val _sortByAcc = MutableLiveData(SortOption.Descending.value)   // 거래대금 정렬
    val sortByAcc: LiveData<Int> get() = _sortByAcc

    data class Ticket(val ticket: String)
    data class Type(val type: String, val codes: List<String>)

    fun getInitTicker() {
        onIO {
            getTickerUseCase()
                .catch { Log.e("MainViewModel: ", it.message ?: "") }
                .map {
                    // 기본 정렬 거래대금 순
                    it.sortedByDescending { ticker -> ticker.acc_trade_price_24h }
                }
                .collect {
                    // 초기 리스트 세팅
                    _tickerListResult.value = it
                    // 웹소켓 시작
                    marketCodeList.addAll(it.map { ticker -> ticker.market })
                    startWebSocketUseCase(orderbookListener)
                }
        }
    }

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

            _tickerListResult.value = _tickerListResult.value.map { existingTicker ->
                if (existingTicker.code == ticker.code) {
                    ticker.acc_trade_price_24h = existingTicker.acc_trade_price_24h
                    ticker.anim = true

                    ticker
                } else {
                    existingTicker
                }
            }
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
            Log.e("MainViewModel", t.message ?: "")
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
        }
    }

    fun updatePrice(data: Int) {
        _sortByPrice.value = data
    }

    fun updateAcc(data: Int) {
        _sortByAcc.value = data
    }

    fun onSort(id: Int, value: Int) {
//        _tickerListResult.value = emptyList()
        when (id) {
            R.id.sort_price_ -> {
                when (value) {
                    SortOption.Descending.value -> {
                        _tickerListResult.value =
                            _tickerListResult.value.sortedByDescending { ticker -> ticker.trade_price }
                    }

                    SortOption.Ascending.value -> {
                        _tickerListResult.value =
                            _tickerListResult.value.sortedBy { ticker -> ticker.trade_price }
                    }
                }
            }

            R.id.sort_acc_ -> {
                when (value) {
                    SortOption.Descending.value -> {
                        _tickerListResult.value =
                            _tickerListResult.value.sortedByDescending { ticker -> ticker.acc_trade_price_24h }
                    }

                    SortOption.Ascending.value -> {
                        _tickerListResult.value =
                            _tickerListResult.value.sortedBy { ticker -> ticker.acc_trade_price_24h }
                    }
                }
            }

            else -> {}
        }
    }

    override fun onCleared() {
        super.onCleared()
        // viewModel 소멸 시 웹소켓 종료
        stopWebSocketUseCase()
    }
}