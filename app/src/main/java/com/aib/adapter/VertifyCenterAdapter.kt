package com.aib.adapter

import com.alibaba.android.vlayout.layout.SingleLayoutHelper
import com.wm.collector.R
import com.wm.collector.databinding.ItemVertifyCenterBinding

class VertifyCenterAdapter : BaseDelegateAdapter<ItemVertifyCenterBinding>() {
    override fun attachToParent(): Boolean = false

    override fun getResId(): Int = R.layout.item_vertify_center

    override fun getCount(): Int = 1

    override fun getLayoutHelper(): SingleLayoutHelper = SingleLayoutHelper()

    override fun bindView(binding: ItemVertifyCenterBinding, position: Int) {

    }
}