package com.app.test_upbit_websocket.base

import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.app.test_upbit_websocket.BR

abstract class BaseActivity<VDB : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity() {
    abstract val viewModel: VM
    protected lateinit var binding: VDB
    protected lateinit var context: Context

    abstract fun setup()

    abstract fun onCreateView(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        setup()
        onCreateView(savedInstanceState)
    }

    open fun setBinding(@LayoutRes layoutId: Int) {
        binding = DataBindingUtil.setContentView<VDB>(this, layoutId).apply {
            setVariable(BR.viewModel, viewModel)
            setVariable(BR.view, this@BaseActivity)
            lifecycleOwner = this@BaseActivity
        }
    }
}