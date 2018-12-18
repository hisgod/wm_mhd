package com.aib.view.activity

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.ContentResolver
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.JsonToken
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.aib.entity.ContactEntity
import com.aib.entity.RelationEntity

import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ThreadUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.Gson


import com.aib.utils.Constants
import com.aib.viewmodel.RelationVm
import com.authreal.api.AuthBuilder
import com.authreal.api.OnResultListener
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bqs.crawler.cloud.sdk.OnLoginResultListener
import com.wm.collector.R
import com.wm.collector.databinding.ActivityRelationBinding
import java.util.*

/**
 * 人际关系
 */
class RelationActivity : BaseActivity<ActivityRelationBinding>() {
    private lateinit var vm: RelationVm
    internal var relations: MutableList<String> = ArrayList()
    private val CONTACTS1 = 0
    private val CONTACTS2 = 1

    override fun getResId(): Int {
        return R.layout.activity_relation
    }

    override fun initData(savedInstanceState: Bundle?) {

        vm = getViewModel(RelationVm::class.java)

        relations.add("父母")
        relations.add("配偶")
        relations.add("亲戚")

        binding.presenter = Presenter()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val uri = data!!.data
            val contacts = getPhoneContacts(uri)
            if (requestCode == CONTACTS1) {
                binding.tvName1.text = contacts!![0]
                if (TextUtils.isEmpty(contacts[1])) {
                    binding.tvPhone1.text = contacts[0]
                } else {
                    val phone = contacts[1]!!.replace(" ".toRegex(), "")
                    val finalPhone = phone.replace("-".toRegex(), "")
                    binding.tvPhone1.text = finalPhone
                }
            } else if (requestCode == CONTACTS2) {
                binding.tvName2.text = contacts!![0]
                if (TextUtils.isEmpty(contacts[1])) {
                    binding.tvPhone2.text = contacts[0]
                } else {
                    val phone = contacts[1]!!.replace(" ".toRegex(), "")
                    val finalPhone = phone.replace("-".toRegex(), "")
                    binding.tvPhone2.text = finalPhone
                }
            }
        }
    }

    private fun getPhoneContacts(uri: Uri?): Array<String?>? {
        val contact = arrayOfNulls<String>(2)
        //得到ContentResolver对象
        val cr = contentResolver
        //取得电话本中开始一项的光标
        val cursor = cr.query(uri!!, null, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
            //取得联系人姓名
            val nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            contact[0] = cursor.getString(nameFieldColumnIndex)
            //取得电话号码
            val ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
            val phone = cr.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId,
                null,
                null
            )
            if (phone != null) {
                if (phone.moveToFirst()) {
                    contact[1] = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                }
            }
            phone!!.close()
            cursor.close()
        } else {
            return null
        }
        return contact
    }


    inner class Presenter {
        /**
         * 返回
         *
         * @param view
         */
        fun back(view: View) {
            finish()
        }

        /**
         * 填写完资料，下一步
         *
         * @param view
         */
        fun enterStatus(view: View) {
            val getRelation1 = binding.tvRelation1.text.toString().trim()
            val getName1 = binding.tvName1.text.toString().trim()
            val getPhone1 = binding.tvPhone1.text.toString().trim()
            val getRelation2 = binding.tvRelation2.text.toString().trim()
            val getName2 = binding.tvName2.text.toString().trim()
            val getPhone2 = binding.tvPhone2.text.toString().trim()

            if (getRelation1 == "请选择") {
                ToastUtils.showShort("请选择第一联系人关系")
                return
            }

            if (getName1 == "请选择") {
                ToastUtils.showShort("请选择第一联系人")
                return
            }

            if (getRelation2 == "请选择") {
                ToastUtils.showShort("请选择第二联系人关系")
                return
            }

            if (getName2 == "请选择") {
                ToastUtils.showShort("请选择第二联系人")
                return
            }

            val fisrtRelation = RelationEntity(getRelation1, getName1, getPhone1)
            val secondRelation = RelationEntity(getRelation2, getName2, getPhone2)
            val relationEntities = ArrayList<RelationEntity>()
            relationEntities.add(fisrtRelation)
            relationEntities.add(secondRelation)
            val json = Gson().toJson(relationEntities)
            vm.putEmergencyContact(json, SPUtils.getInstance().getString(Constants.TOKEN))
                .observe(this@RelationActivity, Observer { stringBaseEntity ->
                    if (stringBaseEntity!!.code == 1) {
                        ToastUtils.showShort(stringBaseEntity.msg)
                        ActivityUtils.startActivity(WorkInfoActivity::class.java)
                        finish()
                    } else {
                        ToastUtils.showShort(stringBaseEntity.msg)
                        finish()
                    }
                })
        }

        /**
         * 第一联系人
         *
         * @param view
         */
        fun showRelation1(view: View) {
            KeyboardUtils.hideSoftInput(this@RelationActivity)
            val pvOptions =
                OptionsPickerBuilder(this@RelationActivity, OnOptionsSelectListener { options1, options2, options3, v ->
                    //返回的分别是三个级别的选中位置
                    binding.tvRelation1.text = relations[options1]
                })
                    .setTitleText("家属关系")
                    .setDividerColor(Color.RED)
                    .setTextColorCenter(Color.RED) //设置选中项文字颜色
                    .setContentTextSize(16)
                    .build<String>()

            pvOptions.setPicker(relations)//三级选择器
            pvOptions.show()
        }

        /**
         * 第二联系人关系
         *
         * @param view
         */
        fun showRelation2(view: View) {
            KeyboardUtils.hideSoftInput(this@RelationActivity)
            val pvOptions =
                OptionsPickerBuilder(this@RelationActivity, OnOptionsSelectListener { options1, options2, options3, v ->
                    //返回的分别是三个级别的选中位置
                    binding.tvRelation2.text = relations[options1]
                })
                    .setTitleText("家属关系")
                    .setDividerColor(Color.RED)
                    .setTextColorCenter(Color.RED) //设置选中项文字颜色
                    .setContentTextSize(16)
                    .build<String>()

            pvOptions.setPicker(relations)//三级选择器
            pvOptions.show()
        }

        /**
         * 打开联系人列表
         *
         * @param view
         */
        fun openContact(view: View) {
            if (view.id == binding.rlRelation1.id) {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = ContactsContract.Contacts.CONTENT_TYPE
                startActivityForResult(intent, CONTACTS1)
            } else if (view.id == binding.rlRelation2.id) {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = ContactsContract.Contacts.CONTENT_TYPE
                startActivityForResult(intent, CONTACTS2)
            }
        }
    }
}
