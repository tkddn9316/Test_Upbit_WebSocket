package com.app.test_upbit_websocket

import android.os.Bundle
import androidx.activity.viewModels
import com.app.test_upbit_websocket.base.BaseActivity
import com.app.test_upbit_websocket.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val viewModel: MainViewModel by viewModels()

    override fun setup() {
        setBinding(R.layout.activity_main)
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        viewModel.getInitTicker()
    }
}