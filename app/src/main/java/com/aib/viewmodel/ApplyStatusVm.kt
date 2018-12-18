package com.aib.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.aib.entity.BaseEntity
import com.aib.entity.WaitVerifyEntity
import com.aib.entity.ApplyStatusEntity
import com.aib.net.ApiService
import com.aib.net.Resource
import javax.inject.Inject


class ApplyStatusVm @Inject constructor() : ViewModel() {
    @Inject
    lateinit var apiService: ApiService

    /**
     * 获取申请信息
     */
    fun getApplyInfo(): LiveData<Resource<BaseEntity<List<WaitVerifyEntity>>>> {
        val data = MutableLiveData<Resource<BaseEntity<List<WaitVerifyEntity>>>>()
//        apiService!!.APPLY_STATUS_AD()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(object : RxObserver<BaseEntity<List<WaitVerifyEntity>>>() {
//                    override fun onStart() {
//                        data.value = Resource.loading(null)
//                    }
//
//                    override fun onSuccess(listBaseEntity: BaseEntity<List<WaitVerifyEntity>>) {
//                        data.value = Resource.success(listBaseEntity)
//                    }
//
//
//                    override fun onFailure(t: Throwable) {
//                        data.value = Resource.error(t.message, null)
//                    }
//                })
        return data
    }

    /**
     * 查询申请状态
     *
     * @param token
     * @return
     */
    fun isApply(token: String): LiveData<BaseEntity<ApplyStatusEntity>> {
        val data = MutableLiveData<BaseEntity<ApplyStatusEntity>>()
//        apiService!!
//                .APPLY_STATUS(token)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread(), true)
//                .subscribe(object : Observer<BaseEntity<ApplyStatusEntity>> {
//                    override fun onSubscribe(d: Disposable) {
//
//                    }
//
//                    override fun onNext(applyStatusEntityBaseEntity: BaseEntity<ApplyStatusEntity>) {
//                        data.postValue(applyStatusEntityBaseEntity)
//                    }
//
//                    override fun onError(e: Throwable) {
//
//                    }
//
//                    override fun onComplete() {
//
//                    }
//                })
        return data
    }
}
