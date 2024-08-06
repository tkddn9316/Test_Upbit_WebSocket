package com.app.test_upbit_websocket.base

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.domain.model.Ticker

abstract class BaseListAdapter<VH : RecyclerView.ViewHolder, M : Any>(
    val context: Context
) : ListAdapter<M, VH>(DiffCallback<M>()) {

    abstract fun bind(holder: VH, item: M)

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position)
        bind(holder, item)
    }

    class DiffCallback<M : Any> : DiffUtil.ItemCallback<M>() {
        override fun areItemsTheSame(oldItem: M, newItem: M): Boolean {
            // 리스트 아이템 변경 시 필요한 부분만 변경되도록(code끼리 비교)
            return (oldItem as? Ticker)?.code == (newItem as? Ticker)?.code
        }

        override fun areContentsTheSame(oldItem: M, newItem: M): Boolean {
            return oldItem == newItem
        }
    }
}