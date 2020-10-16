package com.kinglyl.library.activity;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import androidx.core.view.ViewCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.kinglyl.library.R;


/**
 * 这个是5.0的下拉刷新风格
 * Created by long on 2015/3/25 0025.
 */
public class BaseRefreshLayout extends SwipeRefreshLayout {
    GestureDetector mGestureDetector;


    private TouchHolder holder;

    private int step;

    public BaseRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
        setColorSchemeResources(R.color.holo_red_light,
                R.color.holo_green_light, R.color.holo_orange_dark, R.color.holo_blue_dark);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                holder = new TouchHolder();
                step = 0;
                holder.startX = event.getX();
                holder.startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                if (step < 2) {
                    step += 1;
                    holder.endX = event.getX();
                    holder.endY = event.getY();
                    holder.calculateMoveEvent();
                }

                return super.onInterceptTouchEvent(event) && !holder.canScrolled;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.onInterceptTouchEvent(event);
    }

    protected boolean canChildScrollHorizontally(View v, boolean checkV, float dx, float x, float y) {
        if (v instanceof ViewGroup) {
            final ViewGroup group = (ViewGroup) v;

            final int count = group.getChildCount();
            // Count backwards - let topmost views consume scroll distance first.
            for (int i = count - 1; i >= 0; i--) {
                final View child = group.getChildAt(i);

                final int childLeft = child.getLeft() + supportGetTranslationX(child);
                final int childRight = child.getRight() + supportGetTranslationX(child);
                final int childTop = child.getTop() + supportGetTranslationY(child);
                final int childBottom = child.getBottom() + supportGetTranslationY(child);

                if (x >= childLeft && x < childRight && y >= childTop && y < childBottom
                        && canChildScrollHorizontally(child, true, dx, x - childLeft, y - childTop)) {
                    return true;
                }
            }
        }

        return checkV && (ViewCompat.canScrollVertically(v, (int) dx) || v instanceof AbsListView);
    }

    private int supportGetTranslationX(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return (int) v.getTranslationX();
        }

        return 0;
    }

    private int supportGetTranslationY(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            return (int) v.getTranslationY();
        }

        return 0;
    }

    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            boolean isX = distanceY < distanceX;
            boolean canScrollX = canChildScrollHorizontally(BaseRefreshLayout.this, false, distanceX, e2.getX(), e2.getY());
            return isX && canScrollX;
        }
    }

    class TouchHolder {

        boolean lock = false;

        boolean canScrolled = true;

        private float startX, startY, endX, endY;

        public void calculateMoveEvent() {
            float distanceX = Math.abs(endX - startX);
            float distanceY = Math.abs(endY - startY);
            boolean isX = distanceY <= distanceX;
            boolean canScrollX = canChildScrollHorizontally(BaseRefreshLayout.this, false, distanceX, endX, endY);
            canScrolled = isX && canScrollX;
        }
    }

}
