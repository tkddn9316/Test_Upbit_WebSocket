package com.app.test_upbit_websocket.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.app.test_upbit_websocket.R
import com.app.test_upbit_websocket.base.BaseActivity
import com.app.test_upbit_websocket.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import com.app.test_upbit_websocket.util.Util.SortOption
import com.app.test_upbit_websocket.adapter.KrwMarketListAdapter

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
        binding.list.itemAnimator = null

        viewModel.getInitTicker()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            // 현재가 정렬
            R.id.sort_price_ -> {
                val value = viewModel.sortByPrice.value ?: SortOption.Nothing.value
                if (value == SortOption.Ascending.value) {
                    viewModel.updatePrice(SortOption.Descending.value)
                } else {
                    viewModel.updatePrice(value + 1)
                }
                viewModel.updateAcc(SortOption.Nothing.value)
                viewModel.onSort(
                    R.id.sort_price_,
                    viewModel.sortByPrice.value ?: SortOption.Nothing.value
                )
            }
            // 거래대금 정렬
            R.id.sort_acc_ -> {
                val value = viewModel.sortByAcc.value ?: SortOption.Nothing.value
                if (value == SortOption.Ascending.value) {
                    viewModel.updateAcc(SortOption.Descending.value)
                } else {
                    viewModel.updateAcc(value + 1)
                }
                viewModel.updatePrice(SortOption.Nothing.value)
                viewModel.onSort(
                    R.id.sort_acc_,
                    viewModel.sortByAcc.value ?: SortOption.Nothing.value
                )
            }
            else -> super.onClick(v)
        }

    }
}