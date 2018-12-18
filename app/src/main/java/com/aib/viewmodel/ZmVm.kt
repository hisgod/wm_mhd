package com.aib.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.v4.util.ArrayMap
import com.aib.entity.BaseEntity
import com.aib.entity.NextStepEntity
import com.aib.net.ApiService
import com.aib.utils.Constants
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ZmVm @Inject constructor() : ViewModel() {
    @Inject
    lateinit var apiService: ApiService

    /**
     * 提交图片
     */
    fun postImg(filePath: String): LiveData<BaseEntity<String>> {
        val data = MutableLiveData<BaseEntity<String>>()
        val file = FileUtils.getFileByPath(filePath)
        val requestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val build = MultipartBody.Builder()
            .addFormDataPart("imgFile", file.name, requestBody)
            .addFormDataPart("token", SPUtils.getInstance().getString(Constants.TOKEN))
            .setType(MultipartBody.FORM)
            .build()
        apiService.POST_ZM_IMG(build)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread(), true)
            .subscribe(object : Observer<BaseEntity<String>> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: BaseEntity<String>) {
                    data.value = t
                }

                override fun onError(e: Throwable) {
                }
            })
        return data
    }

    /**
     * 上传芝麻认证信息
     */
    fun postZmInfo(params: ArrayMap<String, Any>): LiveData<BaseEntity<String>> {
        val data = MutableLiveData<BaseEntity<String>>()
        apiService
            .POST_ZM_INFO(params)
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
                }
            })
        return data
    }

    /**
     * 查询下一步操作
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
}