package com.kinglyl.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.kinglyl.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 顶部的切换按钮
 */

public class TopChangeView extends LinearLayout {

    @BindView(R.id.view_top_change_btn1)
    TextView btn1;

    @BindView(R.id.view_top_change_btn2)
    TextView btn2;

    @BindView(R.id.view_top_btn1_line)
    View btnLine1;

    @BindView(R.id.view_top_btn2_line)
    View btnLine2;

    private int btnType = 1;
    private TopViewClickListener topViewClickListener;

    public TopChangeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.view_top_change, this);
        ButterKnife.bind(this);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TopChangeView_Attr);

        String leftTextStr = ta.getString(R.styleable.TopChangeView_Attr_topLeftText);
        String rightTextStr = ta.getString(R.styleable.TopChangeView_Attr_topRightText);

        btn1.setText(leftTextStr);
        btn2.setText(rightTextStr);

        ta.recycle();

        setButtonStyle(1);
    }

    private void setButtonStyle(int btn) {
        if (btn == 1) {
            btnLine1.setVisibility(VISIBLE);
            btnLine2.setVisibility(INVISIBLE);

            btn1.setTextColor(getResources().getColor(R.color.wave_blue_main));
            btn2.setTextColor(getResources().getColor(R.color.text_color_secondary));
        } else {
            btnLine1.setVisibility(INVISIBLE);
            btnLine2.setVisibility(VISIBLE);

            btn1.setTextColor(getResources().getColor(R.color.text_color_secondary));
            btn2.setTextColor(getResources().getColor(R.color.wave_blue_main));
        }
        btnType = btn;
    }

    public int getBtnType() {
        return btnType;
    }

    @OnClick(R.id.view_top_change_btn1)
    void clickBtn1() {
        if (btnType == 1) {
            return;
        }
        setButtonStyle(1);
        if (topViewClickListener != null) {
            topViewClickListener.clickLeftButton();
        }
    }

    @OnClick(R.id.view_top_change_btn2)
    void clickBtn2() {
        if (btnType == 2) {
            return;
        }
        setButtonStyle(2);
        if (topViewClickListener != null) {
            topViewClickListener.clickRightButton();
        }
    }

    public void setOnTopViewClickListener(TopViewClickListener listener) {
        topViewClickListener = listener;
    }

    public interface TopViewClickListener {
        void clickLeftButton();

        void clickRightButton();
    }
}
