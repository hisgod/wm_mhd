package com.aib.view.fragment

import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import com.aib.utils.Constants
import com.aib.viewmodel.CertainFragmentVm
import com.wm.collector.R
import com.wm.collector.databinding.FragmentCertainBinding
import com.aib.view.activity.*
import com.authreal.api.AuthBuilder
import com.authreal.api.OnResultListener
import com.blankj.utilcode.util.*
import java.util.*
import javax.inject.Inject

/**
 * 一定下款
 */
class CertainFragment : BaseFragment<FragmentCertainBinding>() {

    @Inject
    lateinit var vm: CertainFragmentVm

    private var dialog: ProgressDialog? = null

    override fun getResId(): Int = R.layout.fragment_certain

    override fun initData() {


        binding.presenter = Presenter()

        dialog = ProgressDialog(activity)
        dialog!!.setMessage("加载中...")

        binding.cb.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.btnStart.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            } else {
                binding.btnStart.setBackgroundColor(resources.getColor(R.color.color_c3c3c3))
            }
        }

        binding.tvAgreement.setOnClickListener {
            val args = Bundle()
            args.putString("AGREEMENT_TYPE", "NEWINSURANCE")
            ActivityUtils.startActivity(args, AgreementActivity::class.java)
        }

    }

    /**
     * 身份证认证
     */
    private fun faceAuth() {
        val id = "demo_" + Date().time
        val mAuthBuilder = AuthBuilder(id, "3ce36316-c66c-401d-b290-8b141eba4a88", "", OnResultListener {
            vm.postIdInfo(SPUtils.getInstance().getString(Constants.TOKEN), it)
                .observe(this@CertainFragment, Observer {
                    if (it!!.code == 1) {
                        ToastUtils.showShort(it.msg)
                        ActivityUtils.startActivity(RelationActivity::class.java)
                    } else {
                        ToastUtils.showShort(it.msg)
                    }
                })
        })
        mAuthBuilder.faceAuth(activity)
    }

    /**
     * 获取用户下一个步骤
     */
    private fun getNextStatus(result: String?) {
        dialog!!.show()
        vm.nextStep(result!!).observe(this, Observer {
            if (!it!!.data.finish) {
                if (it.code == 1) {
                    when (it.data.nextStep) {
                        "Identity" -> {
                            //身份认证
                            faceAuth()
                        }
                        "Contacts" -> {
                            //联系人认证
                            ActivityUtils.startActivity(RelationActivity::class.java)
                        }

                        "Zhima" -> {
                            //芝麻认证
                            ActivityUtils.startActivity(ZmActivity::class.java)
                        }
                        "Work" -> {
                            //工作认证
                            ActivityUtils.startActivity(WorkInfoActivity::class.java)
                        }
                    }
                    dialog!!.dismiss()
                } else {
                    ToastUtils.showShort(it.msg)
                }
            } else {
                dialog!!.dismiss()
                ActivityUtils.startActivity(WaitActivity::class.java)   //进入等待审核页面
            }
        })
    }

    inner class Presenter {
        /**
         * 立即申请
         *
         * @param view
         */
        fun apply(view: View) {
            if (binding.cb.isChecked) {
                ThreadUtils.executeByIo(object : ThreadUtils.SimpleTask<String>() {
                    override fun onSuccess(result: String?) {
                        if (result != "") {
                            getNextStatus(result)
                        } else {
                            ActivityUtils.startActivity(LoginActivity::class.java)
                        }
                    }

                    override fun doInBackground(): String? = SPUtils.getInstance().getString(Constants.TOKEN)
                })
            } else {
                ToastUtils.showShort("请勾选投保须知协议")
            }
        }
    }
}
