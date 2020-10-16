package com.kinglyl.android;

import android.app.ActivityManager;
import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.http.HttpResponseCache;

import androidx.multidex.MultiDexApplication;

import com.blankj.utilcode.util.Utils;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.lzf.easyfloat.EasyFloat;
import com.orhanobut.hawk.Hawk;
import com.simga.library.utils.HawkKey;
import com.simga.library.utils.SdCardUtil;
import com.simga.library.utils.glide.GlideUtil;
import com.tencent.bugly.crashreport.CrashReport;
import com.zzhoujay.richtext.RichText;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import cn.jiguang.share.android.api.JShareInterface;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

public class App extends MultiDexApplication {

    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();//分配的可用内存
    private static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;
    public static Context app;

    public static SharedPreferences getSp(String spName) {
        return app.getSharedPreferences(spName, Context.MODE_PRIVATE);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Utils.init(this);
        //初始化项目文件夹
        SdCardUtil.initFileDir(this);
        //图片框架初始化
        GlideUtil.init(this);
        //初始化hawk
        Hawk.init(this).build();
        //初始化极光
        JPushInterface.setDebugMode(true);
        JShareInterface.init(this);

        setSoundAndVibrate(Hawk.get(HawkKey.IS_OPEN_SOUND, true),
                Hawk.get(HawkKey.IS_OPEN_VIBRATE, true));
        //初始化bugly
        CrashReport.initCrashReport(this);
        initRichText();
        initLiveEventBus();
        initFloatView();
        initFresco();
        initSvgaHttpCache();
    }


    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    private void initFresco() {
        Fresco.shutDown();
        // 初始化图片加载器模块
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this)
                .setMaxCacheSize(500 * 1024 * 1024)
                .setMaxCacheSizeOnLowDiskSpace(100 * 1024 * 1024)
                .setBaseDirectoryName("image_cache")
                .setBaseDirectoryPath(getExternalCacheDir())
                .build();

        //内存配置
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                MAX_MEMORY_CACHE_SIZE,  // 内存缓存中总图片的最大大小,以字节为单位。
                128,                    // 内存缓存中图片的最大数量。
                MAX_MEMORY_CACHE_SIZE,  // 内存缓存中准备清除但尚未被删除的总图片的最大大小,以字节为单位。
                128,                    // 内存缓存中准备清除的总图片的最大数量。
                Integer.MAX_VALUE);     // 内存缓存中单个图片的最大大小。

        Supplier<MemoryCacheParams> mSupplierMemoryCacheParams = () -> bitmapCacheParams;

        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory.newBuilder(this, new OkHttpClient())
                .setMainDiskCacheConfig(diskCacheConfig)
                .setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams)
                .setEncodedMemoryCacheParamsSupplier(mSupplierMemoryCacheParams)
                .build();
        Fresco.initialize(this, config);
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

    private void initSvgaHttpCache() {
        try {
            //SVGA缓存
            File cacheDir = new File(getApplicationContext().getCacheDir(), "http");
            HttpResponseCache.install(cacheDir, 1024 * 1024 * 128);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initFloatView() {
        EasyFloat.init(this, BuildConfig.DEBUG);
    }

    private void initLiveEventBus() {
        LiveEventBus.get()
                .config()
                .supportBroadcast(this)
                .lifecycleObserverAlwaysActive(false)
                .autoClear(true);
    }

    private void initRichText() {
        RichText.initCacheDir(this);
    }

    /**
     * 根据Pid获取当前进程的名字，一般就是当前app的包名
     */
    private String getAppName(int pid) {
        String processName = null;
        ActivityManager activityManager =
                (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List list = activityManager.getRunningAppProcesses();
        Iterator i = list.iterator();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info =
                    (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pid) {
                    // 根据进程的信息获取当前进程的名字
                    processName = info.processName;
                    // 返回当前进程名
                    return processName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 没有匹配的项，返回为null
        return null;
    }
}