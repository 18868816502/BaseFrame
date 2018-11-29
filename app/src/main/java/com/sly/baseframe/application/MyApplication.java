package com.sly.baseframe.application;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.sly.baseframe.manager.UserManager;
import com.sly.baseframe.util.umeng.ChannelUtil;
import com.sly.baseframe.util.umeng.Umeng;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by xst on 2018/9/17.
 */

public class MyApplication extends Application {
    private static Context mContext;

    public static Context getApplication() {
        return mContext;
    }

    public static String sChannelId = "unknown";

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        initFresco();
        initARouter();
        initLoginManager();
        initUmeng();
        initJPush();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    /**
     * 初始化Fresco
     */
    private void initFresco() {
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, config);
    }

    private void initARouter() {
        ARouter.openLog();     // 打印日志
//        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this);
    }

    //初始化用户数据
    private void initLoginManager() {
        UserManager.getInstance();
    }

    private void initUmeng() {
        sChannelId = ChannelUtil.getChannel(this);
        if (sChannelId != null) {
            ApplicationInfo appInfo = null;
            try {
                appInfo = this.getPackageManager()
                        .getApplicationInfo(getPackageName(),
                                PackageManager.GET_META_DATA);

                String umengAppkey = appInfo.metaData.getString("UMENG_APPKEY");
                MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(this, umengAppkey, sChannelId));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        Umeng.install(this);
    }

    private void initJPush() {
        JPushInterface.setDebugMode(true);  // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);          // 初始化 JPush
    }
//
}
