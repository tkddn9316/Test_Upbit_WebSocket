package com.app.test_upbit_websocket

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.test_upbit_websocket.base.BaseActivity
import com.app.test_upbit_websocket.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

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
    }
}