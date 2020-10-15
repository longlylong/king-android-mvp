package com.simga.library.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * 尺寸转化
 */
public class ScreenUtil {

    public static int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue, context.getResources().getDisplayMetrics());
    }

    public static float px2dp(Context context, float pxValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,
                pxValue, context.getResources().getDisplayMetrics());
    }
//    public static float px2dip(Context context, float pxValue) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (pxValue / scale + 0.5f);
//    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(float pxValue, float fontScale) {
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(float spValue, float fontScale) {
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕宽，单位为dip
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高，单位为dip
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕高度，包括底部导航栏
     */
    public static int getRealScreenHeight(Activity activity) {
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealMetrics(displayMetrics);
        } else {
            display.getMetrics(displayMetrics);
        }
        return displayMetrics.heightPixels;
    }

    /**
     * 用于获取状态栏的高度。 使用Resource对象获取（推荐这种方式）
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static void fixScreen(Context context, View view) {
        if (!isNormalScreen(context)) {
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom() + dp2px(context, 50));
        }
    }

    private static boolean isNormalScreen(Context context) {
        int screenHeight = getScreenHeight(context);
        return !"meizu".equalsIgnoreCase(Build.BRAND) &&
                (screenHeight == 1920 || screenHeight == 1280 || screenHeight > 1920
                        || screenHeight == 800 || screenHeight == 854 || screenHeight == 960);
    }
}
