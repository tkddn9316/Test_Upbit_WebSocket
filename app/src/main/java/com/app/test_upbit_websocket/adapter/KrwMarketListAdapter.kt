package com.app.test_upbit_websocket.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.domain.model.Ticker
import com.app.test_upbit_websocket.R
import com.app.test_upbit_websocket.base.BaseListAdapter
import com.app.test_upbit_websocket.databinding.ItemKrwMarketBinding
import com.app.test_upbit_websocket.util.Util

class KrwMarketListAdapter(context: Context) :
    BaseListAdapter<KrwMarketListAdapter.ItemViewHolder, Ticker>(context) {

    override fun bind(holder: ItemViewHolder, item: Ticker) {
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemKrwMarketBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    inner class ItemViewHolder(val binding: ItemKrwMarketBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Ticker) {
            binding.apply {
                model = data
                root.tag = data

                binding.price.setTextColor(setColor(data))

                // RecyclerView의 DataBinding 지연없이 즉시 실행
                executePendingBindings()
            }
        }
    }

    fun setColor(data: Ticker): Int {
        return when (data.change) {
            Util.ColorType.RISE.name -> context.getColor(R.color.red)
            Util.ColorType.FALL.name -> context.getColor(R.color.blue)
            else -> context.getColor(R.color.black_700)
        }
    }
}