package com.app.data.model

import com.google.gson.annotations.SerializedName

data class MarketDTO(
    val markets: List<Market>
) {
    data class Market(
        // 업비트에서 제공중인 시장 정보
        @SerializedName("market")
        val market: String
    )
}
