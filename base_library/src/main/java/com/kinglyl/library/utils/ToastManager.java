package com.kinglyl.library.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public final class ToastManager {

    private static Toast mToast;

    public static void show(Context mContext, Object message) {
        if (message == null) return;
        TextView title;
        if (mToast == null) {
            View layout = View.inflate(mContext, com.kinglyl.library.R.layout.layout_toast, null);
            title = layout.findViewById(com.kinglyl.library.R.id.toast_tv);
            title.setText(message.toString());
            mToast = Toast.makeText(mContext, message + "", Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setText(message.toString());
            mToast.setView(layout);
        } else {
            title = mToast.getView().findViewById(com.kinglyl.library.R.id.toast_tv);
            title.setText(message.toString());
        }
        mToast.show();
    }
}
