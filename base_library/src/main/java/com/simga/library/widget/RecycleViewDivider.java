package com.simga.library.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.simga.library.R;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


/**
 * PackageName : com.library.widget
 * Author : Helen Liu
 * Date : 2017/03/08
 * Time : 20:29
 */
public class RecycleViewDivider extends RecyclerView.ItemDecoration {

    private Drawable mDivider;     //分割线Drawable
    private int mDividerHeight;  //分割线高度

    /**
     * 使用line_divider中定义好的颜色
     *
     * @param context
     * @param dividerHeight 分割线高度
     */
    public RecycleViewDivider(Context context, int dividerHeight) {
        mDivider = ContextCompat.getDrawable(context, R.drawable.line_divider);
        mDividerHeight = dividerHeight;
    }

    /**
     * @param context
     * @param divider       分割线Drawable
     * @param dividerHeight 分割线高度
     */
    public RecycleViewDivider(Context context, Drawable divider, int dividerHeight) {
        if (divider == null) {
            mDivider = ContextCompat.getDrawable(context, R.drawable.line_divider);
        } else {
            mDivider = divider;
        }
        mDividerHeight = dividerHeight;
    }

    //获取分割线尺寸
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, mDividerHeight);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDividerHeight;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
