package com.aib.view.activity

import android.app.Activity
import android.arch.lifecycle.Observer
import android.graphics.Color
import android.os.Bundle
import android.support.v4.util.ArrayMap
import android.text.TextUtils
import android.view.View
import com.aib.utils.Constants
import com.aib.viewmodel.ZmVm
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.wm.collector.R
import com.wm.collector.databinding.ActivityZmBinding
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.api.widget.Widget
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.Flowable
import io.reactivex.functions.Function
import top.zibin.luban.Luban


/**
 * 芝麻分认证
 */
class ZmActivity : BaseActivity<ActivityZmBinding>() {

    private lateinit var vm: ZmVm
    private var fileUrl: String? = null //文件URL

    override fun getResId(): Int = R.layout.activity_zm

    override fun initData(savedInstanceState: Bundle?) {
        vm = getViewModel(ZmVm::class.java)

        binding.presenter = Presenter()
    }

    inner class Presenter {
        fun back(view: View) {
            finish()
        }

        /**
         * 打开相册
         */
        fun openAlbum(view: View) {
            Album.image(this@ZmActivity)
                .singleChoice()
                .widget(
                    Widget.newDarkBuilder(this@ZmActivity)
                        .title("相册")
                        .statusBarColor(resources.getColor(R.color.colorAccent))
                        .toolBarColor(resources.getColor(R.color.colorAccent))
                        .navigationBarColor(resources.getColor(R.color.colorAccent))
                        .bucketItemCheckSelector(Color.BLACK, resources.getColor(R.color.colorAccent))
                        .mediaItemCheckSelector(Color.BLACK, resources.getColor(R.color.colorAccent))
                        .build()
                )
                .camera(true)
                .columnCount(4)
                .filterSize(null)
                .filterMimeType(null)
                .afterFilterVisibility(false)
                .onResult({ result ->
                    if (!result.isEmpty()) {
                        val filePath = result[0].path

                        Flowable.just(filePath)
                            .observeOn(Schedulers.io())
                            .map(object : Function<String, String> {
                                override fun apply(t: String): String {
                                    return Luban.with(this@ZmActivity).load(t).get().get(0).path
                                }
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe { item ->
                                vm.postImg(item).observe(this@ZmActivity, Observer {
                                    if (it!!.code == 1) {
                                        ToastUtils.showShort(it.msg)
                                        fileUrl = it.data
                                    } else {
                                        ToastUtils.showShort(it.msg)
                                    }
                                    Glide.with(this@ZmActivity).load(item).into(binding.iv)
                                })
                            }
                    }
                })
                .onCancel({
                    ToastUtils.showShort("取消")
                })
                .start()
        }

        /**
         * 下一步
         */
        fun nextStep(view: View) {
            val getContent = binding.etContent.text.toString().trim()

            if (TextUtils.isEmpty(getContent)) {
                ToastUtils.showShort("芝麻分不能为空")
                return
            }

            if (TextUtils.isEmpty(fileUrl)) {
                ToastUtils.showShort("请添加芝麻分图片")
                return
            }

            val data = ArrayMap<String, Any>()
            data.put("imgUrl", fileUrl)
            data.put("token", SPUtils.getInstance().getString(Constants.TOKEN))
            data.put("zhimaScore", getContent.toInt())
            vm.postZmInfo(data).observe(this@ZmActivity, Observer {
                if (it!!.code == 1) {
                    ToastUtils.showShort(it.msg)
                    vm.nextStep(SPUtils.getInstance().getString(Constants.TOKEN)).observe(this@ZmActivity, Observer {
                        if (it!!.data.finish) {
                            ActivityUtils.startActivity(WaitActivity::class.java)   //进入等待审核页面
                            finish()
                        } else {
                            finish()
                        }
                    })
                } else {
                    ToastUtils.showShort(it.msg)
                    finish()
                }
            })
        }
    }
}
