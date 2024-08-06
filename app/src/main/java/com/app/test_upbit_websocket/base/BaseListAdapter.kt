package com.app.test_upbit_websocket.base

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.domain.model.Ticker

//abstract class BaseListAdapter<VH : RecyclerView.ViewHolder, M : Any>(
//    val context: Context
//) : RecyclerView.Adapter<VH>() {
//    private var data = mutableListOf<M>()
//
//    fun getData(): MutableList<M> {
//        return data
//    }
//
//    open fun setData(items: List<M>) {
//        data.clear().also { data.addAll(items) }
//        notifyDataSetChanged()
//    }
//
//    fun getItem(index: Int): M {
//        return data[index]
//    }
//
//    override fun getItemCount(): Int {
//        return if (data.isEmpty()) {
//            0
//        } else {
//            data.size
//        }
//    }
//
//    fun clear() {
//        if (data.isNullOrEmpty()) {
//            data.clear().also { notifyDataSetChanged() }
//        }
//    }
//
//    open fun removeData(position: Int) {
//        data.removeAt(position).also { notifyItemRemoved(position) }
//    }
//}


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
            return oldItem == newItem // Implement proper comparison
        }

        override fun areContentsTheSame(oldItem: M, newItem: M): Boolean {
            return oldItem == newItem // Implement proper comparison
        }
    }
}