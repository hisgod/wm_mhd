package com.aib.adapter

import com.alibaba.android.vlayout.layout.SingleLayoutHelper
import com.wm.collector.R
import com.wm.collector.databinding.ItemVertifyCenterBinding
import com.wm.collector.databinding.ItemVertifyTopBinding

class VertifyTopAdapter : BaseDelegateAdapter<ItemVertifyTopBinding>() {
    override fun attachToParent(): Boolean = true

    override fun getResId(): Int = R.layout.item_vertify_top

    override fun getCount(): Int = 1

    override fun getLayoutHelper(): SingleLayoutHelper = SingleLayoutHelper()

    override fun bindView(binding: ItemVertifyTopBinding, position: Int) {

    }
}