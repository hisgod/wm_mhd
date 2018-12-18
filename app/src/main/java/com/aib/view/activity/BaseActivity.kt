package com.aib.view.activity

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.aib.utils.Extendsion
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity<D : ViewDataBinding> : AppCompatActivity(), Extendsion, HasSupportFragmentInjector {
    lateinit var binding: D
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = createBinding(getResId())
        initData(savedInstanceState)
    }

    abstract fun getResId(): Int

    abstract fun initData(savedInstanceState: Bundle?)

    /**
     * 生成对应的ViewModel对象
     *
     * @param cls
     * @param <T>
     * @return
    </T> */
    fun <T : ViewModel> getViewModel(cls: Class<out T>): T {
        return ViewModelProviders.of(this, factory).get(cls)
    }

    /**
     * Dagger2全局配置
     *
     */
    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onDestroy() {
        super.onDestroy()
        //解除绑定
        if (binding != null) {
            binding!!.unbind()
        }
    }
}