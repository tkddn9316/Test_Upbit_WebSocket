package com.app.test_upbit_websocket

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.test_upbit_websocket.base.BaseActivity
import com.app.test_upbit_websocket.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    private lateinit var adapter: KrwMarketListAdapter

    override val viewModel: MainViewModel by viewModels()

    override fun setup() {
        setBinding(R.layout.activity_main)
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        adapter = KrwMarketListAdapter(context)
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(context)

        viewModel.getInitTicker()

//        lifecycleScope.launch {
//            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
//                launch {
//                    viewModel.placesListResult.collect { items ->
//                        adapter.setData(items)
//                    }
//                }
//            }
//        }
    }
}