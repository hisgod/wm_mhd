package com.aib.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import com.aib.entity.BaseEntity
import com.aib.entity.UserEntity
import com.aib.net.ApiService
import com.blankj.utilcode.util.ToastUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject

class LoginVm @Inject constructor() : ViewModel() {

    @Inject
    lateinit var apiService: ApiService

    /**
     * 登陆
     *
     * @param phone
     * @param pwd
     */
    fun login(phone: String, pwd: String): LiveData<BaseEntity<UserEntity>> {
        val data = MutableLiveData<BaseEntity<UserEntity>>()
        apiService
                .USER_LOGIN(phone, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<BaseEntity<UserEntity>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onNext(t: BaseEntity<UserEntity>) {
                        data.postValue(t)
                    }

                    override fun onError(e: Throwable) {
                        ToastUtils.showShort(e.message)
                    }
                })
        return data
    }
}
