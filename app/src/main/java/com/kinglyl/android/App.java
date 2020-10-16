package com.kinglyl.android;

import android.app.Notification;

import com.lzf.easyfloat.EasyFloat;
import com.orhanobut.hawk.Hawk;
import com.kinglyl.library.activity.BaseApp;
import com.kinglyl.library.utils.HawkKey;
import com.tencent.bugly.crashreport.CrashReport;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

public class App extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化极光
        JPushInterface.setDebugMode(true);
        JShareInterface.init(this);

        setSoundAndVibrate(Hawk.get(HawkKey.IS_OPEN_SOUND, true),
                Hawk.get(HawkKey.IS_OPEN_VIBRATE, true));
        //初始化bugly
        CrashReport.initCrashReport(this);
        initFloatView();
    }

    private void setSoundAndVibrate(boolean isOpenSound, boolean isOpenVibrate) {
        BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(app);
        builder.statusBarDrawable = R.mipmap.ic_launcher;//设置推送的图标
        if (isOpenVibrate && !isOpenSound) {//只有振动
            builder.notificationDefaults = Notification.DEFAULT_VIBRATE;
        } else if (isOpenSound && !isOpenVibrate) {//只有声音
            builder.notificationDefaults = Notification.DEFAULT_SOUND;
        } else if (isOpenSound && isOpenVibrate) {//两个都有
            builder.notificationDefaults = Notification.DEFAULT_ALL;
        } else {//只有呼吸灯
            builder.notificationDefaults = Notification.DEFAULT_LIGHTS;
        }
        JPushInterface.setDefaultPushNotificationBuilder(builder);
    }

    private void initFloatView() {
        EasyFloat.init(this, BuildConfig.DEBUG);
    }
}