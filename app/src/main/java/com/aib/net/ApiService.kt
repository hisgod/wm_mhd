package com.aib.net


import android.support.v4.util.ArrayMap
import com.aib.entity.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {

    companion object {
//                var baseUrl = "http://192.168.2.124:8080/HYLoan_App/"   //本地
        var baseUrl = "http://loan.smoneybag.com:8190/HYLoan_App/"   //正式
    }

    /**
     * 上传单张文件
     *
     * @return
     */
    @POST("appUser/editHeadImg")
    fun UPLOAD_SINGLE_FILE(@Body body: MultipartBody): Observable<BaseEntity<String>>

    /**
     * 退出登录
     *
     * @param token
     * @return
     */
    @POST("appUser/loginOut")
    @FormUrlEncoded
    fun USER_EXIT(@Field("token") token: String): Observable<BaseEntity<String>>


    /**
     * 获取贷款详情
     *
     * @param productId
     * @return
     */
    @POST("appProduct/getDetail")
    @FormUrlEncoded
    fun LOAN_DETAIL(@Field("productId") productId: Int): Observable<BaseEntity<LoanDetailEntity>>


    /**
     * 获取短信验证码
     *
     * @param phone
     * @param type
     * @return
     */
    @POST("sms/send/verification/code")
    @FormUrlEncoded
    fun VERTIFICATION_CODE(@Field("phone") phone: String, @Field("type") type: Int): Observable<BaseEntity<String>>

    /**
     * 免登录
     *
     * @return
     */
    @POST("appUser/autoLogin")
    @FormUrlEncoded
    fun LOGIN_FREE(@Field("token") token: String): Observable<BaseEntity<UserEntity>>

    /**
     * 注册
     *
     * @param phone
     * @param pwd
     * @param code
     * @param smsToken //验证码返回的smsToken
     * @param type     //标识注册还是修改密码
     * @return
     */
    @POST("appUser/register")
    @FormUrlEncoded
    fun USER_REGISTER(
        @Field("phone") phone: String, @Field("password") pwd: String, @Field("code") code: String, @Field(
            "smsToken"
        ) smsToken: String?, @Field("sysChannelUserId") type: Int
    ): Observable<BaseEntity<String>>

    /**
     * 登录
     *
     * @return
     */
    @POST("appUser/login")
    @FormUrlEncoded
    fun USER_LOGIN(@Field("phone") phone: String, @Field("pwd") pwd: String): Observable<BaseEntity<UserEntity>>

    /**
     * 忘记密码
     *
     * @param phone
     * @param code
     * @param pwd
     * @param smsToken
     * @return
     */
    @POST("appUser/forgetPwd")
    @FormUrlEncoded
    fun FORGET_PWD(@Field("phone") phone: String, @Field("pwd") pwd: String, @Field("code") code: String, @Field("smsToken") smsToken: String?): Observable<BaseEntity<String>>

    /**
     * 用户协议
     *
     * @param text
     * @return
     */
    @GET("common/getAppAgreement")
    fun AGREEMENT(@Query("onlyLabel") text: String): Observable<BaseEntity<AgreementEntity>>

    /**
     * 贷款详情点击量
     *
     * @param productId
     * @return
     */
    @POST("appProduct/updateHitCount")
    @FormUrlEncoded
    fun LOAN_CLICK_COUNT(@Field("productId") productId: Int): Observable<BaseEntity<String>>

    /**
     * 提交全部联系人
     */
    @POST("access/contacts/phoneBook/save")
    @FormUrlEncoded
    fun PUT_ALL_CONTACTS(@Field("data") data: String, @Field("token") token: String): Observable<BaseEntity<String>>

    /**
     *工作信息图片
     */
    @POST("access/work/imgUrl")
    fun PUT_WORK_INFO_IMG(@Body body: MultipartBody): Observable<BaseEntity<String>>

    /**
     * 提交工作信息
     */
    @POST("access/work/save")
    @FormUrlEncoded
    fun PUT_WORK_INFO(@FieldMap map: ArrayMap<String, Any>): Observable<BaseEntity<String>>

    /**
     * 保存联系人
     */
    @POST("access/contacts/linkMan/save")
    @FormUrlEncoded
    fun PUT_EMERGENCY_CONTACT(@Field("data") data: String, @Field("token") token: String): Observable<BaseEntity<String>>


    /**
     *获取下一步认证
     */
    @POST("access/step/nextStep")
    @FormUrlEncoded
    fun NEXT_AUTH(@Field("token") token: String): Observable<BaseEntity<NextStepEntity>>


    /**
     *  保存运营商
     */
    @POST("access/operator/save")
    @FormUrlEncoded
    fun POST_OPERATOR(@Field("token") token: String): Observable<BaseEntity<String>>


    /**
     * 提交身份证信息
     */
    @POST("access/identity/save")
    @FormUrlEncoded
    fun POST_ID_INFO(@Field("token") token: String, @Field("udCreditJson") udCreditJson: String): Observable<BaseEntity<String>>

    /**
     *工作信息图片
     */
    @POST("access/zhima/imgUrl")
    fun POST_ZM_IMG(@Body body: MultipartBody): Observable<BaseEntity<String>>

    /**
     * 上传芝麻信息
     */
    @POST("access/zhima/save")
    @FormUrlEncoded
    fun POST_ZM_INFO(@FieldMap data: ArrayMap<String, Any>): Observable<BaseEntity<String>>

    /**
     * 等待审核后面的广告
     *
     * @return
     */
    @POST("appADV/recommend/getLAPlistByCId")
    @FormUrlEncoded
    fun WAIT_VERIFY_AD(@FieldMap params: ArrayMap<String, Any>): Observable<BaseEntity<List<WaitVerifyEntity>>>

    /**
     * 获取页面状态
     */
    @POST("app/user/page/state")
    @FormUrlEncoded
    fun PAGE_STATE(@Field("userId") userId: Int): Observable<BaseEntity<PageStateEntity>>
}
