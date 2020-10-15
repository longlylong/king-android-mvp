package com.simga.library.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class SystemUtil {

    /**
     * 获取当前系统的版本
     *
     * @param context
     * @return
     */
    public static int getVesion(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static String getVersionName(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String getApplicationName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        try {
            packageManager = context.getApplicationContext().getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName =
                (String) packageManager.getApplicationLabel(applicationInfo);
        return applicationName;
    }

    /**
     * 取签名证书信息
     *
     * @param mContext
     * @return
     */
    public static int getSign(Context mContext) {
        int pkgsign = -1;
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(mContext.getPackageName(), PackageManager
                    .GET_SIGNATURES);
            return packageInfo.signatures[0].hashCode();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pkgsign;
    }

}
