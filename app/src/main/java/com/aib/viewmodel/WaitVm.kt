package com.aib.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.v4.util.ArrayMap
import com.aib.entity.BaseEntity
import com.aib.entity.PageStateEntity
import com.aib.entity.WaitVerifyEntity
import com.aib.net.ApiService
import com.blankj.utilcode.util.ToastUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * 等待审核Vm
 */
class WaitVm @Inject constructor() : ViewModel() {

    @Inject
    lateinit var apiService: ApiService

    /**
     * 获取广告数据
     */
    fun getAdJson(params: ArrayMap<String, Any>): LiveData<BaseEntity<List<WaitVerifyEntity>>> {
        val data = MutableLiveData<BaseEntity<List<WaitVerifyEntity>>>()
        apiService
                .WAIT_VERIFY_AD(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<BaseEntity<List<WaitVerifyEntity>>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: BaseEntity<List<WaitVerifyEntity>>) {
                        data.value = t
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.showShort(e.message)
                    }
                })
        return data
    }

    /**
     * 获取页面状态
     */
    fun getPageStateJson(uid: Int): LiveData<BaseEntity<PageStateEntity>> {
        val data = MutableLiveData<BaseEntity<PageStateEntity>>()
        apiService
                .PAGE_STATE(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<BaseEntity<PageStateEntity>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: BaseEntity<PageStateEntity>) {
                        data.value = t
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.showShort(e.message)
                    }
                })
        return data
    }
}