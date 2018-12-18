package com.aib.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.aib.viewmodel.*

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    /**
     * 一定下贷
     */
    @Binds
    @IntoMap
    @ViewModelKey(CertainFragmentVm::class)
    internal abstract fun CertainFragmentVm(vm: CertainFragmentVm): ViewModel

    /**
     * 个人中心
     */
    @Binds
    @IntoMap
    @ViewModelKey(CenterFragmentVm::class)
    internal abstract fun CenterFragmentVm(vm: CenterFragmentVm): ViewModel

    /**
     * 欢迎界面
     */
    @Binds
    @IntoMap
    @ViewModelKey(SplashVm::class)
    internal abstract fun SplashVm(vm: SplashVm): ViewModel

    /**
     * 登录
     */
    @Binds
    @IntoMap
    @ViewModelKey(LoginVm::class)
    internal abstract fun LoginVm(vm: LoginVm): ViewModel

    /**
     *个人中心
     */
    @Binds
    @IntoMap
    @ViewModelKey(PersonalInfoVm::class)
    internal abstract fun PersonalInfoVm(vm: PersonalInfoVm): ViewModel

    /**
     * 注册
     */
    @Binds
    @IntoMap
    @ViewModelKey(RegisterVm::class)
    internal abstract fun RegisterVm(vm: RegisterVm): ViewModel

    /**
     * 协议
     */
    @Binds
    @IntoMap
    @ViewModelKey(AgreementVm::class)
    internal abstract fun AgreementVm(vm: AgreementVm): ViewModel

    /**
     * 忘记密码
     */
    @Binds
    @IntoMap
    @ViewModelKey(ForgetPwdVm::class)
    internal abstract fun ForgetPwdVm(vm: ForgetPwdVm): ViewModel

    /**
     * 人际关系
     */
    @Binds
    @IntoMap
    @ViewModelKey(RelationVm::class)
    internal abstract fun RelationVm(vm: RelationVm): ViewModel

    /**
     * 工作信息
     */
    @Binds
    @IntoMap
    @ViewModelKey(WorkInfoVm::class)
    internal abstract fun WorkInfoVm(vm: WorkInfoVm): ViewModel

    /**
     * 芝麻认证
     */
    @Binds
    @IntoMap
    @ViewModelKey(ZmVm::class)
    internal abstract fun ZmVm(vm: ZmVm): ViewModel

    /**
     * 等待审核
     */
    @Binds
    @IntoMap
    @ViewModelKey(WaitVm::class)
    internal abstract fun WaitVm(vm: WaitVm): ViewModel

    /**
     * 贷款平台详情
     */
    @Binds
    @IntoMap
    @ViewModelKey(LoanDetailVm::class)
    internal abstract fun LoanDetailVm(vm: LoanDetailVm): ViewModel

    /**
     * WebView
     */
    @Binds
    @IntoMap
    @ViewModelKey(WebVm::class)
    internal abstract fun WebVm(vm: WebVm): ViewModel

    /**
     * 消息中心
     */
    @Binds
    @IntoMap
    @ViewModelKey(MsgCenterViewModel::class)
    internal abstract fun MsgCenterViewModel(vm: MsgCenterViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
