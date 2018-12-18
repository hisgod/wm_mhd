package com.aib.di


import com.aib.view.fragment.CertainFragment
import com.aib.view.fragment.CenterFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Fragment模块
 */
@Module
abstract class FragmentModule {
    /**
     * 一定下贷
     */
    @ContributesAndroidInjector
    internal abstract fun CertainFragment(): CertainFragment

    /**
     * 个人中心
     */
    @ContributesAndroidInjector
    internal abstract fun CenterFragment(): CenterFragment
}
