package com.app.test_upbit_websocket

import android.util.Log
import com.app.domain.model.Ticker
import com.app.domain.usecase.GetTickerUseCase
import com.app.test_upbit_websocket.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTickerUseCase: GetTickerUseCase
) : BaseViewModel() {

    private val _placesListResult = MutableStateFlow<List<Ticker>>(emptyList())
    val placesListResult = _placesListResult.asStateFlow()

    fun getInitTicker() {
        onIO {
            getTickerUseCase()
                .catch { Log.e("MainViewModel: ", it.message ?: "") }
                .collect { _placesListResult.value = it }
        }
    }
}