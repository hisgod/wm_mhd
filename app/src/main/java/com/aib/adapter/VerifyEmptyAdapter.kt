package com.aib.adapter

import android.databinding.ViewDataBinding
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.layout.SingleLayoutHelper
import com.wm.collector.R

class VerifyEmptyAdapter : BaseDelegateAdapter<ViewDataBinding>() {
    override fun attachToParent(): Boolean = false

    override fun getResId(): Int = R.layout.item_verify_empty

    override fun getCount(): Int = 1

    override fun getLayoutHelper(): LayoutHelper = SingleLayoutHelper()

    override fun bindView(binding: ViewDataBinding, position: Int) {
    }
}