package com.kinglyl.library.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.kinglyl.library.R;

public class DialogLoading extends Dialog {

    Activity activity;

    public DialogLoading(Context context) {
        super(context, R.style.loadDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading_layout, null);
        setContentView(view);
        setCanceledOnTouchOutside(false);
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public void show() {
        super.show();
    }

    public void show(boolean isCancelable) {
        setCancelable(isCancelable);
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            show();
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
