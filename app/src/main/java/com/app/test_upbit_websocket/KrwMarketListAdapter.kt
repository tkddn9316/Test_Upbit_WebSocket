package com.app.test_upbit_websocket

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.domain.model.Ticker
import com.app.test_upbit_websocket.base.BaseListAdapter
import com.app.test_upbit_websocket.databinding.ItemKrwMarketBinding

//class KrwMarketListAdapter(context: Context) : BaseListAdapter<KrwMarketListAdapter.ItemViewHolder, Ticker>(context) {
//
//    override fun onCreateViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): KrwMarketListAdapter.ItemViewHolder {
//        return ItemViewHolder(
//            ItemKrwMarketBinding.inflate(LayoutInflater.from(context), parent, false)
//        )
//    }
//
//    override fun onBindViewHolder(holder: KrwMarketListAdapter.ItemViewHolder, position: Int) {
//        with(holder) {
//            bind(getItem(position))
//        }
//    }
//
//    inner class ItemViewHolder(val binding: ItemKrwMarketBinding) : RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(data: Ticker) {
//            binding.apply {
//                model = data
//                root.tag = data
//
//                binding.price.setTextColor(setColor(data))
//
//                // RecyclerView의 DataBinding 지연없이 즉시 실행
//                executePendingBindings()
//            }
//        }
//    }
//
//    fun setColor(data: Ticker): Int {
//        return when(data.change) {
//            Util.ColorType.RISE.name -> context.getColor(R.color.red)
//            Util.ColorType.FALL.name -> context.getColor(R.color.blue)
//            else -> context.getColor(R.color.black_700)
//        }
//    }
//
////    override fun setData(items: List<Ticker>) {
////        data.clear().also { data.addAll(items) }
////        notifyDataSetChanged()
////
////        items.forEach {
////            Log.e("AAAAAAA", it.market)
////        }
////    }
//}

class KrwMarketListAdapter(context: Context) : BaseListAdapter<KrwMarketListAdapter.ItemViewHolder, Ticker>(context) {

    override fun bind(holder: ItemViewHolder, item: Ticker) {
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemKrwMarketBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    inner class ItemViewHolder(val binding: ItemKrwMarketBinding) : RecyclerView.ViewHolder(binding.root) {

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
        return when(data.change) {
            Util.ColorType.RISE.name -> context.getColor(R.color.red)
            Util.ColorType.FALL.name -> context.getColor(R.color.blue)
            else -> context.getColor(R.color.black_700)
        }
    }
}