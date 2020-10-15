package com.king.android.view;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.king.android.R;


/**
 * 底进底出的对话框效果
 */
public class BaseDialogAnimActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    protected View getView(int layoutId) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int windowWidth = displayMetrics.widthPixels;
        View view = View.inflate(this, layoutId, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(windowWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        return view;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return super.onTouchEvent(event);
    }

    @Override
    public void finish() {
        overridePendingTransition(R.anim.popup_enter, R.anim.popup_exit);
        super.finish();
    }
}
