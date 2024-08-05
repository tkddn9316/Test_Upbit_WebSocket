package com.app.test_upbit_websocket

import android.util.Log
import com.app.domain.usecase.GetTickerUseCase
import com.app.test_upbit_websocket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTickerUseCase: GetTickerUseCase
) : BaseViewModel() {

    fun getInitTicker() {
        onIO {
            getTickerUseCase()
                .catch { Log.e("MainViewModel: ", it.message ?: "") }
                .collect {
                    it.forEach {
                        Log.e("MainViewModel: ", "${it.code} 그리고 ${it.acc_trade_price_24h}")
                    }
                }
        }
    }
}