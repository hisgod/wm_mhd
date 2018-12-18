package com.aib.adapter

import android.os.Bundle
import com.aib.entity.WaitVerifyEntity
import com.aib.view.activity.LoanDetailActivity
import com.aib.view.activity.WebViewActivity
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SpanUtils
import com.wm.collector.R
import com.wm.collector.databinding.ItemVertifyBottomBinding

class VertifyBottomAdapter : BaseDelegateAdapter<ItemVertifyBottomBinding>() {
    private var data: List<WaitVerifyEntity>? = null

    override fun attachToParent(): Boolean = false

    override fun getResId(): Int = R.layout.item_vertify_bottom

    override fun getCount(): Int = if (data == null) 0 else data!!.size

    override fun getLayoutHelper(): LinearLayoutHelper = LinearLayoutHelper()

    override fun bindView(binding: ItemVertifyBottomBinding, position: Int) {
        val entity = data!![position]
        binding.entity = entity
        binding.executePendingBindings()

        val applyNum = SpanUtils()
            .append(entity.applyCountFake.toString() + "")
            .setForegroundColor(ctx!!.resources.getColor(R.color.color_ff9600))
            .append("人已经申请")
            .create()
        binding.tvApply.text = applyNum

        binding.ll.setOnClickListener {
            val args = Bundle()
            args.putString("URL", entity.requestUrl)
            args.putString("LOAN_TITLE", entity.name)
            ActivityUtils.startActivity(args, WebViewActivity::class.java)
        }
    }

    fun passData(data: List<WaitVerifyEntity>) {
        this.data = data
    }
}