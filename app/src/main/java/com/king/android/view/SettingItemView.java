package com.king.android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.king.android.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SettingItemView extends LinearLayout {

    @BindView(R.id.setting_item_lefticon)
    ImageView mLeftIcon;

    @BindView(R.id.setting_item_lefttext)
    TextView mLeftText;

    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.item_setting, this);
        ButterKnife.bind(this);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.Setting_attr);

        int leftIconResId = ta.getResourceId(R.styleable.Setting_attr_leftIcon, 0);
        String leftTextStr = ta.getString(R.styleable.Setting_attr_leftText);

        mLeftIcon.setImageResource(leftIconResId);
        mLeftText.setText(leftTextStr);

        ta.recycle();
    }
}
