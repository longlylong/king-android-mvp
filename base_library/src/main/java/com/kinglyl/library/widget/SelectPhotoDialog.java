package com.kinglyl.library.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;

import com.kinglyl.library.R;

public class SelectPhotoDialog extends Dialog {

    BGButton buttonOne;
    BGButton buttonTwo;
    private BGButton buttonThree;
    private onClickItem onClickItem;

    public SelectPhotoDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
        this.setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_bottomhint);
        buttonOne = findViewById(R.id.buttonOne);
        buttonTwo = findViewById(R.id.buttonTwo);
        buttonThree = findViewById(R.id.buttonThree);
        initLisetener();
    }

    private void initLisetener() {
        buttonThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        buttonOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickItem != null) {
                    onClickItem.onOneClick();
                }
                dismiss();
            }
        });
        buttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickItem != null) {
                    onClickItem.onTwoClick();
                }
                dismiss();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.bottom_int_out_dialog_style); // 添加动画
        DisplayMetrics dm = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(dm);
        window.setLayout(dm.widthPixels, window.getAttributes().height);
        window.setBackgroundDrawable(new ColorDrawable(0x00000000));
    }

    public void setOnClickItem(SelectPhotoDialog.onClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }


    public interface onClickItem {
        void onOneClick();

        void onTwoClick();

    }
}
