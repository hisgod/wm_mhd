package com.aib.di


import com.aib.view.activity.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * 全部Activity模块
 */
@Module(includes = arrayOf(FragmentModule::class))
internal abstract class ActivityModule {
    /**
     * 首页
     */
    @ContributesAndroidInjector
    internal abstract fun MainActivity(): MainActivity

    /**
     * 等待审核
     */
    @ContributesAndroidInjector
    internal abstract fun WaitActivity(): WaitActivity

    /**
     * 欢迎界面
     */
    @ContributesAndroidInjector
    internal abstract fun SplashActivity(): SplashActivity

    /**
     * 引导页
     */
    @ContributesAndroidInjector
    internal abstract fun GuideActivity(): GuideActivity

    /**
     * 登录
     */
    @ContributesAndroidInjector
    internal abstract fun LoginActivity(): LoginActivity

    /**
     * 联系人
     */
    @ContributesAndroidInjector
    internal abstract fun RelationActivity(): RelationActivity

    /**
     * 个人中心
     */
    @ContributesAndroidInjector
    internal abstract fun PersonalInfoActivity(): PersonalInfoActivity

    /**
     * 客服中心
     */
    @ContributesAndroidInjector
    internal abstract fun CustomerActivity(): CustomerActivity

    /**
     * 注册
     */
    @ContributesAndroidInjector
    internal abstract fun RegisterActivity(): RegisterActivity

    /**
     * 协议
     */
    @ContributesAndroidInjector
    internal abstract fun AgreementActivity(): AgreementActivity

    /**
     * 忘记密码
     */
    @ContributesAndroidInjector
    internal abstract fun ForgetPwdActivity(): ForgetPwdActivity

    /**
     * 工作信息
     */
    @ContributesAndroidInjector
    internal abstract fun WorkInfoActivity(): WorkInfoActivity

    /**
     * 芝麻认证
     */
    @ContributesAndroidInjector
    internal abstract fun ZmActivity(): ZmActivity

    /**
     * 贷款平台详情
     */
    @ContributesAndroidInjector
    internal abstract fun LoanDetailActivity(): LoanDetailActivity

    /**
     * WebView
     */
    @ContributesAndroidInjector
    internal abstract fun WebViewActivity(): WebViewActivity

    /**
     * 消息中心
     */
    @ContributesAndroidInjector
    internal abstract fun MsgCenterActivity(): MsgCenterActivity
}
