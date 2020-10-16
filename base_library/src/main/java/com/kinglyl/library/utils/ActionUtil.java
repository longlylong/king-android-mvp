package com.kinglyl.library.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.ClipboardManager;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.kinglyl.library.R;
import com.kinglyl.library.activity.BaseApp;

public class ActionUtil {

    private static long lastClickTime;

    public static void openBrowser(String url) {
        try {
            if (TextUtils.isEmpty(url)) {
                return;
            }
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            BaseApp.app.startActivity(i);
        } catch (Exception ignored) {
        }
    }

    /**
     * 打电话的了
     */
    public static void callPhone(Context mContext, String num) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + num));
        mContext.startActivity(intent);
    }

    /**
     * 发送短信
     */
    public static void sendSms(Context mContext, String num) {
        Uri uri = Uri.parse("smsto:" + num);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        // intent.putExtra("sms_body", "The SMS text");
        mContext.startActivity(intent);
    }

    public static void copyText(Context context, String text) {
        ClipboardManager mClipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        mClipboard.setText(text);
        ToastManager.show(context, "复制成功");
    }

    /**
     * 判断双击的 默认555ms
     */
    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(555);
    }

    public static boolean isFastDoubleClick(int deltaTime) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < deltaTime) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static void lookPswByEditView(EditText mPasswordEditView, ImageView mShowPswImageView) {
        int inputType = mPasswordEditView.getInputType();
        if (inputType == 129) {
            mPasswordEditView.setInputType(InputType.TYPE_CLASS_TEXT);
            mShowPswImageView.setImageResource(R.mipmap.login_cant_see);
        } else {
            mPasswordEditView.setInputType(129);
            mShowPswImageView.setImageResource(R.mipmap.login_can_see);
        }
        mPasswordEditView.setSelection(mPasswordEditView.getText().length());
    }

}
