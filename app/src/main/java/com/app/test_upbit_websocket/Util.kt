package com.app.test_upbit_websocket

object Util {

    enum class ColorType {
        RISE, EVEN, FALL
    }

    enum class SellColor(val value: String) {
        BUY("BID"), SELL("ASK")
    }

    enum class SortOption {
        Ascending, Nothing, Descending
    }
}