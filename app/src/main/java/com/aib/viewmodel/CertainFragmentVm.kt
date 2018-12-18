package com.aib.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import com.aib.entity.BaseEntity
import com.aib.entity.NextStepEntity
import javax.inject.Inject
import com.aib.entity.ApplyStatusEntity
import android.arch.lifecycle.LiveData
import com.aib.net.ApiService
import com.blankj.utilcode.util.ToastUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class CertainFragmentVm @Inject constructor() : ViewModel() {
    @Inject
    lateinit var apiService: ApiService

    /**
     * 下一个步骤
     *
     * @param token
     */
    fun nextStep(token: String): MutableLiveData<BaseEntity<NextStepEntity>> {
        val data = MutableLiveData<BaseEntity<NextStepEntity>>()
        apiService
                .NEXT_AUTH(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<BaseEntity<NextStepEntity>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(t: BaseEntity<NextStepEntity>) {
                        data.value = t
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.showShort(e.message)
                    }
                })
        return data
    }

    /**
     * 提交身份证信息
     */
    fun postIdInfo(token: String, msg: String): LiveData<BaseEntity<String>> {
        val data = MutableLiveData<BaseEntity<String>>()
        apiService
                .POST_ID_INFO(token, msg)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<BaseEntity<String>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: BaseEntity<String>) {
                        data.value = t
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.showShort(e.message)
                    }
                })
        return data
    }
}
