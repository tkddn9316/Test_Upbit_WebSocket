package com.app.test_upbit_websocket.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.domain.model.Ticker
import com.app.domain.usecase.GetTickerUseCase
import com.app.domain.usecase.SortTickersByAccUseCase
import com.app.domain.usecase.SortTickersByPriceUseCase
import com.app.test_upbit_websocket.R
import com.app.test_upbit_websocket.base.BaseViewModel
import com.app.test_upbit_websocket.util.Util.SortOption
import com.app.test_upbit_websocket.util.WebSocketManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTickerUseCase: GetTickerUseCase,
    private val webSocketManager: WebSocketManager,
    private val sortTickersByPriceUseCase: SortTickersByPriceUseCase,
    private val sortTickersByAccUseCase: SortTickersByAccUseCase
) : BaseViewModel() {

    private val _tickerListResult = MutableStateFlow<List<Ticker>>(emptyList())
    val tickerListResult = _tickerListResult.asStateFlow()

    private val _sortByPrice = MutableLiveData(SortOption.Nothing.value)    // 현재가 정렬
    val sortByPrice: LiveData<Int> get() = _sortByPrice
    private val _sortByAcc = MutableLiveData(SortOption.Descending.value)   // 거래대금 정렬
    val sortByAcc: LiveData<Int> get() = _sortByAcc

    init {
        observeWebSocketMessages()
    }

    fun getInitTicker() {
        onIO {
            getTickerUseCase()
                .catch { Log.e("MainViewModel: ", it.message ?: "") }
                .map {
                    // 기본 정렬 거래대금 순
                    it.sortedByDescending { ticker -> ticker.acc_trade_price_24h }
                        .onEach { ticker -> ticker.anim = false }
                }
                .collect {
                    // 초기 리스트 세팅
                    _tickerListResult.value = it
                    // 웹소켓 시작
//                    marketCodeList.addAll(it.map { ticker -> ticker.market })
//                    startWebSocketUseCase(orderbookListener)
                    webSocketManager.start(it.map { ticker -> ticker.market })
                }
        }
    }

    private fun observeWebSocketMessages() {
        viewModelScope.launch {
            webSocketManager.webSocketMessages.collect { ticker ->
                ticker?.let { updateTickerList(it) }
            }
        }
    }

    private fun updateTickerList(ticker: Ticker) {
        _tickerListResult.value = _tickerListResult.value.map { existingTicker ->
            if (existingTicker.code == ticker.code) {
//                ticker.acc_trade_price_24h = existingTicker.acc_trade_price_24h
//                ticker.anim = true
//                ticker
                ticker.acc_trade_price_24h = existingTicker.acc_trade_price_24h
                // 기존 데이터와 가격이 다를 때만 애니메이션 적용
                ticker.anim = existingTicker.trade_price != ticker.trade_price

                ticker
            } else {
                existingTicker
            }
        }
    }

    fun updatePrice(data: Int) {
        _sortByPrice.value = data
    }

    fun updateAcc(data: Int) {
        _sortByAcc.value = data
    }

    fun onSort(id: Int, value: Int) {
        when (id) {
            R.id.sort_price_ -> {
                _tickerListResult.value = sortTickersByPriceUseCase(_tickerListResult.value, value)
            }

            R.id.sort_acc_ -> {
                _tickerListResult.value = sortTickersByAccUseCase(_tickerListResult.value, value)
            }

            else -> {}
        }
    }

    override fun onCleared() {
        super.onCleared()
        // viewModel 소멸 시 웹소켓 종료
        webSocketManager.stop()
    }
}

//    private val orderbookListener = object : WebSocketListener() {
//        override fun onOpen(webSocket: WebSocket, response: Response) {
//            super.onOpen(webSocket, response)
//
//            // 보낼 메시지 세팅하기
//            val ticket = Ticket("ticker")
//            val type = Type("trade", marketCodeList)
//            val gson = gson.toJson(arrayListOf(ticket, type))
//            sendWebSocketUseCase(gson)
//        }
//
//        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
//            super.onMessage(webSocket, bytes)
//
//            // 메시지 받음
//            val bytesToString = bytes.toByteArray().decodeToString()
//            val ticker = gson.fromJson(bytesToString, Ticker::class.java)
//
//            _tickerListResult.value = _tickerListResult.value.map { existingTicker ->
//                if (existingTicker.code == ticker.code) {
//                    ticker.acc_trade_price_24h = existingTicker.acc_trade_price_24h
//                    ticker.anim = true
//
//                    ticker
//                } else {
//                    existingTicker
//                }
//            }
//        }
//
//        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
//            super.onFailure(webSocket, t, response)
//            Log.e("MainViewModel", t.message ?: "")
//        }
//
//        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
//            super.onClosed(webSocket, code, reason)
//        }
//    }


// - ViewModel 내부에 data가 있어 추후 유지보수에 어려움이 있는 코드라고 판단됩니다.
//
// ViewModel 내부에 로직이 집중되고 데이터와 관련된 처리를 모두 ViewModel에서 직접 관리할 경우, 코드의 복잡성이 증가하고 유지보수가 어려워질 수 있습니다.
// 이 문제는 코드의 응집도를 떨어뜨리고, 각 역할이 모호해지는 결과를 초래할 수 있습니다. 이러한 상황에서 ViewModel의 책임을 분리하고,
// SRP(Single Responsibility Principle)를 지키는 것이 중요합니다.
//
// 문제점 요약:
// ViewModel 내에서 데이터 처리 및 정렬 로직이 직접적으로 구현됨:
// ViewModel이 UI 로직과 비즈니스 로직을 모두 담당하고 있습니다. 이는 ViewModel의 역할이 너무 많아져서, 유지보수 시 문제를 일으킬 수 있습니다.
//
// ViewModel이 데이터의 상태 변화를 직접 관리:
// 데이터를 처리하고 정렬하는 작업을 ViewModel에서 직접 하고 있기 때문에, 로직이 복잡해지고 테스트하기 어려워질 수 있습니다.
//
// 데이터를 가공하는 책임이 분리되지 않음:
// 데이터의 가공, 정렬 등의 로직이 ViewModel에 포함되어 있어, 데이터 처리의 책임이 분리되지 않았습니다.
//
// 개선 방법:
// UseCase 또는 Repository를 활용해 데이터 처리 분리:
// 데이터의 가공 및 정렬 작업을 ViewModel에서 분리하여, 별도의 UseCase 또는 Repository에서 처리하도록 만듭니다.
//
// State Handling을 위한 별도 클래스 사용:
// 데이터의 상태 변화를 관리하기 위한 별도의 클래스(예: SortManager)를 만들어서, ViewModel이 아닌 별도의 클래스에서 데이터를 관리하고 ViewModel은 이 클래스를 활용하도록 만듭니다.
//
// ViewModel의 역할 경량화:
// ViewModel의 책임을 데이터와 UI 간의 매개자 역할로 한정하고, 데이터의 로딩, 정렬, 가공은 ViewModel 외부에서 처리합니다.
// ViewModel은 오직 상태 관리 및 UI와의 상호작용에 집중하게 되어, 역할이 명확해집니다.

// - 앱을 첫 실행 시 애니메이션 효과가 적용되는 것을 확인했습니다. 모든 뷰에 애니메이션이 적용되어 부자연스러운 앱 사용성이 보여집니다.
// - 앱을 실행해보고, 시간이 조금 흐른 뒤에 앱을 재실행을 하니 소켓 재연결이 되지 않는 것이 확인 되었습니다.