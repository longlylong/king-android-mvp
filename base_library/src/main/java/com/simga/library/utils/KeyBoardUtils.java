package com.simga.library.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.simga.library.activity.BaseActivity;

/**
 * 软键盘相关辅助类KeyBoardUtils
 */
public class KeyBoardUtils {

    /**
     * 打卡软键盘
     */
    public static void openKeybord(Context mContext, EditText mEditText) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 获取焦点，并弹出软键盘
     */
    public static void showSoftInputFromWindow(BaseActivity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     */
    public static boolean closeKeybord(Context mContext, EditText mEditText) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    public static void hideSoftKeyboard(Context mContext) {
        Activity activity = (Activity) mContext;
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * 调用显示软键盘
     */
    public static void showKeyBoard(Context context, View view) {
        view.setFocusableInTouchMode(true); // 在view获取焦点之前需要把其他已经拥有焦点的控件
        // setFocusable(false)
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 隐藏软键盘
     */
    public static void hideKeyBoard(Activity context) {
        View focusView = context.getCurrentFocus();
        if (focusView != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    focusView.getWindowToken(), 0);
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void hideKeyBoard(Context context, View view) {
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager
                    .hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
