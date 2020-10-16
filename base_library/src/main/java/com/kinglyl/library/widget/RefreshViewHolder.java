package com.kinglyl.library.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kinglyl.library.R;
import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;

public class RefreshViewHolder extends LinearLayout implements IHeaderView {
    private TextView mHeaderStatusTv;
    private ImageView refIcon;
    private String mPullDownRefreshText = "下拉刷新...";
    private String mReleaseRefreshText = "松手刷新...";
    private String mRefreshingText = "加载中...";

    public RefreshViewHolder(Context context) {
        this(context, null);
    }

    public RefreshViewHolder(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshViewHolder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        View headView = View.inflate(getContext(), R.layout.view_refresh_header, null);
        headView.setBackgroundColor(Color.parseColor("#f7f7f7"));
        mHeaderStatusTv = headView.findViewById(R.id.tv_normal_refresh_header_status);
        refIcon = headView.findViewById(R.id.ref_icon);
        mHeaderStatusTv.setText(mPullDownRefreshText);
        refIcon.setImageResource(R.drawable.icon_down);
        addView(headView);
        setGravity(Gravity.CENTER);
        setBackgroundColor(Color.parseColor("#f7f7f7"));
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {
        if (fraction < 1f) {
            mHeaderStatusTv.setText(mPullDownRefreshText);
            refIcon.setImageResource(R.drawable.icon_down);
        }
        if (fraction > 1f) {
            mHeaderStatusTv.setText(mReleaseRefreshText);
            refIcon.setImageResource(R.drawable.icon_up);
        }
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
        if (fraction < 1f) {
            mHeaderStatusTv.setText(mPullDownRefreshText);
            refIcon.setImageResource(R.drawable.icon_down);
        }
    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        mHeaderStatusTv.setText(mRefreshingText);
        refIcon.setImageResource(R.drawable.icon_up);

    }

    @Override
    public void onFinish(OnAnimEndListener listener) {
        listener.onAnimEnd();
    }

    @Override
    public void reset() {
        mHeaderStatusTv.setText(mPullDownRefreshText);
        refIcon.setImageResource(R.drawable.icon_down);
    }
}
