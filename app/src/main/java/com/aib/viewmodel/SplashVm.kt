package com.aib.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import com.aib.entity.BaseEntity
import com.aib.entity.UserEntity
import com.aib.net.ApiService
import com.aib.net.Resource
import com.aib.net.RxObserver
import com.blankj.utilcode.util.ToastUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

class SplashVm @Inject internal constructor() : ViewModel() {
    @Inject
    lateinit var apiService: ApiService

    /**
     * 免登录
     */
    fun loginFree(token: String?): LiveData<Resource<BaseEntity<UserEntity>>> {
        val data = MutableLiveData<Resource<BaseEntity<UserEntity>>>()
        apiService
                .LOGIN_FREE(token!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : RxObserver<BaseEntity<UserEntity>>() {
                    override fun onStart() {

                    }

                    override fun onSuccess(t: BaseEntity<UserEntity>) {
                        data.value = Resource.success(t)
                    }

                    override fun onFailure(t: Throwable) {
                        ToastUtils.showShort(t.message)
                    }
                })
        return data
    }
}
