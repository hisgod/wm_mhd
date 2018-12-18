package com.aib.utils

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import com.aib.view.activity.BaseActivity

/**
 * Kotlin扩展类
 */
interface Extendsion {
    /**
     * Activity生成对应binding的函数
     */
    fun <D : ViewDataBinding> BaseActivity<D>.createBinding(resId: Int): D {
        return DataBindingUtil.setContentView<D>(this, resId)
    }

    /**
     * Adapter中生成对应的binding
     */
    fun <D : ViewDataBinding> ViewGroup.createBinding(resId: Int, isHasParent: Boolean): D {
        val binding: D?
        if (isHasParent) {
            binding = DataBindingUtil.inflate<D>(LayoutInflater.from(this.context), resId, null, isHasParent)
        } else {
            binding = DataBindingUtil.inflate<D>(LayoutInflater.from(this.context), resId, this, isHasParent)
        }
        return binding
    }
}