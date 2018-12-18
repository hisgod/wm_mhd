package com.aib.view.activity

import android.os.Bundle

import android.support.v4.app.Fragment
import com.aib.view.fragment.CertainFragment
import com.aib.utils.Constants
import com.aib.view.fragment.CenterFragment
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ThreadUtils
import com.wm.collector.R
import com.wm.collector.databinding.MainActivityBinding
import java.util.ArrayList

class MainActivity : BaseActivity<MainActivityBinding>() {

    var fragments = ArrayList<Fragment>()

    override fun getResId(): Int = R.layout.main_activity

    override fun initData(savedInstanceState: Bundle?) {
        fragments.add(CertainFragment())
        fragments.add(CenterFragment())

        binding.bnv.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.btn_certain -> {
                    switchFragment(0)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.btn_center -> {
                    switchFragment(1)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }

        switchFragment(0)
    }

    override fun onStart() {
        super.onStart()
        SPUtils.getInstance().put(Constants.FIRST_ENTER_MAIN, true)
    }

    fun switchFragment(position: Int) {
        val ft = supportFragmentManager.beginTransaction()
        for (i in fragments.indices) {
            val fragment = fragments[i]
            if (i == position) {
                if (fragment.isAdded) {
                    ft.show(fragment)
                } else {
                    ft.add(R.id.fl, fragment)
                }
            } else {
                if (fragment.isAdded) {
                    ft.hide(fragment)
                }
            }
        }
        ft.commitNowAllowingStateLoss()
    }
}
