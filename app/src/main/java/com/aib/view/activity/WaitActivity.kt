package com.aib.view.activity

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.util.ArrayMap
import android.support.v7.widget.RecyclerView
import android.view.View
import com.aib.adapter.VerifyEmptyAdapter

import com.aib.adapter.VertifyBottomAdapter
import com.aib.adapter.VertifyCenterAdapter
import com.aib.adapter.VertifyTopAdapter
import com.aib.entity.WaitVerifyEntity
import com.aib.utils.Constants
import com.aib.viewmodel.WaitVm
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.wm.collector.R
import com.wm.collector.databinding.ActivityWaitBinding

/**
 * 等待审核
 */
class WaitActivity : BaseActivity<ActivityWaitBinding>() {
    private lateinit var vm: WaitVm
    private var verifyBottom: VertifyBottomAdapter? = null
    private var verifyTopAdapter: VertifyTopAdapter? = null
    private var verifyCenterAdapter: VertifyCenterAdapter? = null
    private var verifyEmptyAdapter: VerifyEmptyAdapter? = null
    private var adapter: DelegateAdapter? = null
    private var cid = 0
    private var pageNum = 1 //加载更多第几页
    private var data = ArrayList<WaitVerifyEntity>()

    override fun getResId(): Int = R.layout.activity_wait

    override fun initData(savedInstanceState: Bundle?) {
        vm = getViewModel(WaitVm::class.java)

        binding.presenter = Presenter()

        val pool = RecyclerView.RecycledViewPool()
        pool.setMaxRecycledViews(0, 20)
        val manager = VirtualLayoutManager(this)
        binding.rv.setRecycledViewPool(pool)
        binding.rv.layoutManager = manager
        adapter = DelegateAdapter(manager)
        verifyTopAdapter = VertifyTopAdapter()
        verifyCenterAdapter = VertifyCenterAdapter()
        verifyBottom = VertifyBottomAdapter()
        adapter!!.addAdapter(verifyTopAdapter)
        adapter!!.addAdapter(verifyBottom)
        binding.rv.adapter = adapter

        vm.getPageStateJson(SPUtils.getInstance().getInt(Constants.UID)).observe(this, Observer {
            if (it!!.code == 1) {
                if (it.data != null) {
                    if (it.data.qualifiedInfo != null) {
                        cid = it.data.qualifiedInfo.cid
                        getAdJson(cid, pageNum, true)
                    }
                }
            } else {
                ToastUtils.showShort(it.msg)
            }
        })

        binding.srl.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout?) {
                getAdJson(cid, ++pageNum, false)
            }

            override fun onRefresh(refreshLayout: RefreshLayout?) {
                data.clear()
                getAdJson(cid, 1, false)
            }
        })
    }

    private fun getAdJson(cid: Int, pageNum: Int, isFirst: Boolean) {
        val params = ArrayMap<String, Any>()
        params.put("cid", cid)
        params.put("pageNum", pageNum)
        params.put("pageSize", 10)
        vm.getAdJson(params).observe(this, Observer {
            if (it!!.code == 1) {
                data.addAll(it.data)
                verifyBottom!!.passData(data)
                verifyBottom!!.notifyDataSetChanged()

                if (binding.srl.isRefreshing) {
                    binding.srl.finishRefresh(true)
                }

                if (binding.srl.isLoading) {
                    binding.srl.finishLoadMore(true)
                }

                if (it.data.size == 0) {
                    binding.srl.setNoMoreData(true)
                } else {
                    binding.srl.setNoMoreData(false)
                }

                if (isFirst) {
                    if (it.data.size != 0) {
                        adapter!!.addAdapter(1, verifyCenterAdapter)
                        adapter!!.notifyDataSetChanged()
                    } else {
                        verifyEmptyAdapter = VerifyEmptyAdapter()
                        adapter!!.addAdapter(1, verifyEmptyAdapter)
                        adapter!!.notifyDataSetChanged()
                    }
                }
            } else {
                ToastUtils.showShort(it.msg)
            }
        })
    }

    inner class Presenter {
        fun back(view: View) {
            finish()
        }
    }
}
