package com.app.test_upbit_websocket.base

import android.view.View
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.BindingAdapter
import com.app.domain.model.Ticker
import com.app.test_upbit_websocket.R
import com.app.test_upbit_websocket.Util

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("items")
    fun bindRecyclerView(view: RecyclerView, item: List<Any>?) {
        view.adapter?.let {
            (it as BaseListAdapter<*, *>).submitList(item as List<Nothing>)
        }
    }

    @JvmStatic
    @BindingAdapter("updown")
    fun bindUpDown(v: View, value: Ticker?) {
        value?.let {
            if (it.anim) {
                if (Util.SellColor.SELL.value == it.ask_bid) {
                    v.setBackgroundResource(R.drawable.bg_blue_down)
                } else if (Util.SellColor.BUY.value == it.ask_bid) {
                    v.setBackgroundResource(R.drawable.bg_red_up)
                }
                v.postDelayed({
                    it.anim = false
                    v.setBackgroundResource(R.color.transparent)
                }, 300)
            } else {
                v.setBackgroundResource(R.color.transparent)
            }
        } ?: run {
            v.setBackgroundResource(R.color.transparent)
        }
    }
}