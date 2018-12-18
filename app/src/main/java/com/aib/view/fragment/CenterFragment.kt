package com.aib.view.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.aib.view.fragment.BaseFragment
import com.aib.utils.Constants
import com.aib.view.activity.CustomerActivity
import com.aib.view.activity.LoginActivity
import com.aib.view.activity.MsgCenterActivity
import com.aib.view.activity.PersonalInfoActivity
import com.aib.viewmodel.CenterFragmentVm
import com.blankj.utilcode.util.*
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.wm.collector.R
import com.wm.collector.databinding.FragmentCenterBinding

import java.util.ArrayList
import javax.inject.Inject

/**
 * 我的
 */
class CenterFragment : BaseFragment<FragmentCenterBinding>() {

//    private var vm: CenterFragmentVm? = null
    @Inject
    lateinit var vm: CenterFragmentVm

    override fun getResId(): Int {
        return R.layout.fragment_center
    }

    override fun initData() {
//        vm = getViewModel(CenterFragmentVm::class.java)

        //控制器
        binding.presenter = Presenter()
    }

    /**
     * 显示微信对话框
     */
    private fun showWechatDialog() {
        ActivityUtils.startActivity(CustomerActivity::class.java)
    }

    override fun onStart() {
        super.onStart()

        ThreadUtils.executeByIo(object : ThreadUtils.Task<List<String>>() {
            @Throws(Throwable::class)
            override fun doInBackground(): List<String>? {
                val token = SPUtils.getInstance().getString(Constants.TOKEN)
                val headImgPath = SPUtils.getInstance().getString(Constants.HEAD_IMG)
                val account = SPUtils.getInstance().getString(Constants.PHONE)

                val data = ArrayList<String>()
                data.add(token)
                data.add(headImgPath)
                data.add(account)

                return data
            }

            override fun onSuccess(result: List<String>?) {
                if (result!![0] === "") {
                    Glide.with(ctx)
                            .load(R.drawable.ic_default_img)
                            .apply(RequestOptions().circleCrop().placeholder(R.drawable.ic_img_loading).error(R.drawable.ic_img_error))
                            .into(binding.iv)
                    binding.tvPhone.text = "请登录"
                } else {
                    Glide.with(ctx)
                            .load(result!![1])
                            .apply(RequestOptions().circleCrop().placeholder(R.drawable.ic_img_loading).error(R.drawable.ic_img_error))
                            .into(binding.iv)
                    binding.tvPhone.text = result[2]
                }
            }

            override fun onCancel() {

            }

            override fun onFail(t: Throwable) {

            }
        })
    }

    inner class Presenter {
        /**
         * 客服中心
         */
        fun openWechat(view: View) {
            showWechatDialog()
        }

        /**
         * 进入消息中心
         *
         * @param view
         */
        fun enterMsgCnter(view: View) {
            ActivityUtils.startActivity(MsgCenterActivity::class.java)
        }

        /**
         * 进入编辑页面
         *
         * @param view
         */
        fun enterCenter(view: View) {
            if (SPUtils.getInstance().getString(Constants.TOKEN) === "") {
                ActivityUtils.startActivity(LoginActivity::class.java)
            } else {
                ActivityUtils.startActivity(PersonalInfoActivity::class.java)
            }
        }
    }
}
