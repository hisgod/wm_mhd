package com.aib

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.aib.di.DaggerAppComponent
import com.aib.di.Injectable
import com.aib.sdk.album.AlbumMediaLoader
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.bqs.crawler.cloud.sdk.BqsCrawlerCloudSDK
import com.wm.collector.R
import com.yanzhenjie.album.Album
import com.yanzhenjie.album.AlbumConfig
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class BaseApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

//    override fun initSdk() {
//        val intent = Intent(this, InitializeService::class.java)
//        startService(intent)
//
//        //LeakCanary
////        if (LeakCanary.isInAnalyzerProcess(this)) {
////            return;
////        }
////        LeakCanary.install(this);
//    }
//
//    override fun initData() {
//        daggerInject()
//    }
//
//    override fun initPath() {
//        //创建文件夹
//        FileUtils.createOrExistsDir(getCacheDir().getAbsolutePath() + "/img")
//    }


    override fun onCreate() {
        super.onCreate()
        daggerInject()

        //utilcode
        Utils.init(this)

        //album
        Album.initialize(AlbumConfig.newBuilder(this)
                .setAlbumLoader(AlbumMediaLoader())
                .build())

        //极光推送
//        JPushInterface.init(this)

        //百度统计
//        StatService.autoTrace(ctx, true, false)

        //Toast配置
        ToastUtils.setBgColor(resources.getColor(R.color.colorAccent))
        ToastUtils.setMsgColor(Color.WHITE)
        ToastUtils.setMsgTextSize(14)

        //白骑士
        BqsCrawlerCloudSDK.initialize(this)
    }

    /**
     * Dagger2全局注入
     */
    private fun daggerInject() {
        DaggerAppComponent.builder().application(this).build().inject(this)
        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                //Activity注入
                if (activity is HasSupportFragmentInjector) {
                    AndroidInjection.inject(activity)
                }

                //Fragment注入
                if (activity is FragmentActivity) {
                    val appCompatActivity = activity as AppCompatActivity
                    appCompatActivity.supportFragmentManager.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
                            super.onFragmentAttached(fm, f, context)
                            if (f is Injectable) {
                                AndroidSupportInjection.inject(f)
                            }
                        }
                    }, true)
                }

            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }
        })
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }
}