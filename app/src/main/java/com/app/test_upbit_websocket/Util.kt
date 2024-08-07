package com.app.test_upbit_websocket

object Util {

    enum class ColorType {
        RISE, EVEN, FALL
    }

    enum class SellColor(val value: String) {
        BUY("BID"), SELL("ASK")
    }

    enum class SortOption(val value: Int) {
        Nothing(0), Descending(1), Ascending(2)
    }
}