package com.aib.net


import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ToastUtils

import javax.inject.Inject

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class RxObserver<T> : Observer<T> {

    override fun onSubscribe(d: Disposable) {
        if (NetworkUtils.isConnected()) {
            onStart()
        } else {
            ToastUtils.showLong("设备未联网")
        }
    }

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(t: Throwable) {
        onFailure(t)
    }

    override fun onComplete() {

    }

    abstract fun onStart()

    abstract fun onSuccess(t: T)

    abstract fun onFailure(t: Throwable)
}
