package com.simga.library.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.simga.library.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;

/**
 * 改变状态栏的字体颜色--深色或者浅色
 * <p>
 * 能不用就别用。通过反射来实现的，未必能做到全品牌全机型适配。
 * </p>
 * Created by z on 2017/10/23 0023.
 */

public class StatusBarUtil {


    public static void statusBarDarkMode(Activity activity) {
        if (GetSystemUtils.isFlyme()) {
            setMeizuStatusBarDarkIcon(activity, true);
        } else if (GetSystemUtils.isMIUI()) {
            setMiuiStatusBarDarkMode(activity, true);
        } else {
            android6StatusBarDarkMode(activity);
        }
    }

    public static void justChangeBarColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.getWindow().setStatusBarColor(color);   //这里动态修改颜色
        }
    }


    /**
     * android系统版本是是否大于4.4
     *
     * @return
     */
    public static boolean isSetFillBar() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return false;
        }
        return true;
    }

    //字体颜色为黑色。
    public static void android6StatusBarDarkMode(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    //2.对于miui
    //字体颜色为黑色。
    public static boolean setMiuiStatusBarDarkMode(Activity activity, boolean dark) {
        //5.0以上才改，以下就默认黑色状态栏，白色字体
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return false;
        }
        boolean result = false;
        Window window = activity.getWindow();
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
                //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (dark) {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    } else {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }
            } catch (Exception e) {}
        }
        return result;
    }

    //3.对于flyme:
    //字体颜色为黑色。
    public static boolean setMeizuStatusBarDarkIcon(Activity activity, boolean dark) {
        //5.0以上才改，以下就默认黑色状态栏，白色字体
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return false;
        }
        boolean result = false;
        if (activity != null) {
            try {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                activity.getWindow().setAttributes(lp);
                result = true;
//                activity.getWindow().setStatusBarColor(Color.WHITE);   //这里动态修改颜色
            } catch (Exception e) {
            }
        }
        return result;
    }

    public static void isSetFullScreen(Activity activity) {
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private static int statusHeight;

    //获取状态栏高度
    public static int getStatusHeight(Context context) {
        if (statusHeight <= 0) {
            Resources r = context.getResources();
            try {
                @SuppressLint("PrivateApi") Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                Object object = clazz.newInstance();
                int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
                statusHeight = r.getDimensionPixelOffset(height);
                if (statusHeight <= 0) {
                    int resouresId = r.getIdentifier("status_bar_height", "dimen", "android");
                    if (resouresId > 0) {
                        statusHeight = r.getDimensionPixelOffset(resouresId);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

}
