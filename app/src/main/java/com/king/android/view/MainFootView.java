package com.king.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.king.android.R;
import com.king.android.view.menu.MenuItem;
import com.king.android.view.menu.MenuType;
import com.king.android.view.menu.OnMenuItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 首页的脚步
 * Created by long on 2015/1/7 0007.
 */
public class MainFootView extends LinearLayout {

    @BindView(R.id.foot_a)
    View mAView;

    @BindView(R.id.foot_b)
    View mBView;

    @BindView(R.id.foot_c)
    View mCView;

    @BindView(R.id.foot_d)
    View mDView;

    @BindView(R.id.foot_my_red_point)
    View mDNewRedPoint;

    @BindView(R.id.foot_a_icon)
    ImageView footAIcon;
    @BindView(R.id.foot_b_icon)
    ImageView footBIcon;
    @BindView(R.id.foot_c_icon)
    ImageView footCIcon;
    @BindView(R.id.foot_d_icon)
    ImageView footDIcon;

    @BindView(R.id.foot_a_text)
    TextView footAText;
    @BindView(R.id.foot_b_text)
    TextView footBText;
    @BindView(R.id.foot_c_text)
    TextView footCText;
    @BindView(R.id.foot_d_text)
    TextView footDText;

    private OnMenuItemClickListener mMenuItemClickListener;

    public MainFootView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.view_main_foot, this);
        ButterKnife.bind(this);
    }

    public void setOnFootClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        mMenuItemClickListener = onMenuItemClickListener;
    }

    public void setMyRedPointVisible(boolean visible) {
        mDNewRedPoint.setVisibility(visible ? VISIBLE : GONE);
    }

    public void setClickedBg(MenuItem item) {
        footAIcon.setImageResource(R.mipmap.load_image_default);
        footBIcon.setImageResource(R.mipmap.load_image_default);
        footCIcon.setImageResource(R.mipmap.load_image_default);
        footDIcon.setImageResource(R.mipmap.load_image_default);

        footAText.setTextColor(getResources().getColor(R.color.foot_no_clicked));
        footBText.setTextColor(getResources().getColor(R.color.foot_no_clicked));
        footCText.setTextColor(getResources().getColor(R.color.foot_no_clicked));
        footDText.setTextColor(getResources().getColor(R.color.foot_no_clicked));

        switch (item.menuType) {
            case ItemA:
                footAIcon.setImageResource(R.mipmap.load_image_failed);
                footAText.setTextColor(getResources().getColor(R.color.wave_blue_main));
                break;

            case ItemB:
                footBIcon.setImageResource(R.mipmap.load_image_failed);
                footBText.setTextColor(getResources().getColor(R.color.wave_blue_main));
                break;

            case ItemC:
                footCIcon.setImageResource(R.mipmap.load_image_failed);
                footCText.setTextColor(getResources().getColor(R.color.wave_blue_main));
                break;

            case ItemD:
                footDIcon.setImageResource(R.mipmap.load_image_failed);
                footDText.setTextColor(getResources().getColor(R.color.wave_blue_main));
                break;

        }
    }

    @OnClick(R.id.foot_a)
    void clickFootA() {
        if (mMenuItemClickListener != null) {
            mMenuItemClickListener.onClick(new MenuItem(MenuType.ItemA));
        }
    }

    @OnClick(R.id.foot_b)
    void clickFootB() {
        if (mMenuItemClickListener != null) {
            mMenuItemClickListener.onClick(new MenuItem(MenuType.ItemB));
        }
    }

    @OnClick(R.id.foot_c)
    void clickFootC() {
        if (mMenuItemClickListener != null) {
            mMenuItemClickListener.onClick(new MenuItem(MenuType.ItemC));
        }
    }

    @OnClick(R.id.foot_d)
    void clickFootD() {
        if (mMenuItemClickListener != null) {
            mMenuItemClickListener.onClick(new MenuItem(MenuType.ItemD));
        }
    }


}
