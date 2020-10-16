package com.kinglyl.library.utils;

import android.util.Log;

/**
 * Log工具类
 * <p>
 * 建议任何打印日志的地方使用此工具类
 * 用意：软件发布后，方便屏蔽掉所有log日志打印，提高应用的安全性
 * </p>
 * <p>
 * 屏蔽方式：建议采用注释代码的方法。
 * 原因：使用布尔成员标记是否打印日志，容易遭到内存注入等手段的破解。
 * </p>
 */
public class LogUtil {

    public static final String TAG = "112233";

    public static void v(String msg) {
        v(TAG, msg);
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }


    public static void v(String tag, String msg) {
        Log.v(tag, msg + "");
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg + "");
    }

    public static void i(String tag, String msg) {
        Log.i(tag, msg + "");
    }

    public static void w(String tag, String msg) {
        Log.w(tag, msg + "");
    }

    public static void e(String tag, String msg) {
        Log.e(tag, msg + "");
    }

    public static void log(String msg) {
        e(msg);
    }

}
