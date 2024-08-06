package com.app.test_upbit_websocket.base

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.databinding.BindingAdapter

object BindingAdapter {

//    @JvmStatic
//    @BindingAdapter("adapter", "item", requireAll = true)
//    fun bindRecyclerView(view: RecyclerView, adapter: RecyclerView.Adapter<*>, item: List<Any>?) {
//        view.adapter = adapter.apply {
//            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
//            (this as ListAdapter<Any, *>).submitList(item?.toMutableList())
//        }
//    }

    @JvmStatic
    @BindingAdapter("items")
    fun bindRecyclerView(view: RecyclerView, item: List<Any>?) {
//        view.adapter = adapter.apply {
//            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
//            (this as ListAdapter<Any, *>).submitList(item?.toMutableList())
//        }
        view.adapter?.let {
            (it as BaseListAdapter<*, *>).submitList(item as List<Nothing>)
        }
    }

//    @JvmStatic
//    @BindingAdapter("items")
//    fun bindItems(recyclerView: RecyclerView, data: List<*>) {
//        recyclerView.adapter?.let {
//            (it as BaseListAdapter<*, *>).setData(data as List<Nothing>)
//        }
//    }
}