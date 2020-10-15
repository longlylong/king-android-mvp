package com.simga.library.widget.statuslayout;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.simga.library.R;

/**
 * 作者：SimGa
 * 日期：2019/4/29
 * 时间：14:20
 * 包名：com.simga.baselibrary.widget.statuslayout
 */
public class ReplaceLayoutHelper {

    /**
     * 需要替换的 View
     */
    private View contentLayout;
    /**
     * contentLayout 的布局参数
     */
    private ViewGroup.LayoutParams params;
    /**
     * contentLayout 的父 ViewGroup
     */
    private ViewGroup parentLayout;
    /**
     * contentLayout 在 parentLayout 中的位置
     */
    private int viewIndex;
    /**
     * 当前显示的 View
     */
    private View currentLayout;

    public ReplaceLayoutHelper(@NonNull View contentLayout) {
        this.contentLayout = contentLayout;
        getContentLayoutParams();
    }

    /**
     * 获取 contentLayout 的参数信息 LayoutParams、Parent
     */
    private void getContentLayoutParams() {
        this.params = contentLayout.getLayoutParams();
        if (contentLayout.getParent() != null) {
            // 有直接的父控件
            this.parentLayout = (ViewGroup) contentLayout.getParent();
        } else {
            // 认为 contentLayout 是 activity 的跟布局
            // 所以它的父控件就是 android.R.id.content
            this.parentLayout = contentLayout.getRootView().findViewById(android.R.id.content);
        }
        if (parentLayout == null) {
            // 以上两种方法还没有获取到父控件
            // contentLayout 非 activity 的跟布局
            // 父控件就是自己
            if (contentLayout instanceof ViewGroup) {
                parentLayout = (ViewGroup) contentLayout;
                this.viewIndex = 0;
            } else {
                // 否则，contentLayout 是一个非 ViewGroup 的跟布局
                // 该情况，没有办法替换布局，因此不支持
                throw new IllegalArgumentException(contentLayout.getContext().getString(R.string.status_layout_manager_with_illegal_argument));
            }
        } else {
            int count = parentLayout.getChildCount();
            for (int index = 0; index < count; index++) {
                if (contentLayout == parentLayout.getChildAt(index)) {
                    // 获取 contentLayout 在 parentLayout 中的位置
                    this.viewIndex = index;
                    break;
                }
            }
        }
        this.currentLayout = this.contentLayout;
    }

    public void showStatusLayout(View view) {
        if (view == null) {
            return;
        }
        if (currentLayout != view) {
            currentLayout = view;
            ViewGroup parent = (ViewGroup) view.getParent();
            // 去除 view 的 父 view，才能添加到别的 ViewGroup 中
            if (parent != null) {
                parent.removeView(view);
            }
            // 替换 = 移除 + 添加
            parentLayout.removeViewAt(viewIndex);
            parentLayout.addView(view, viewIndex, params);
        }
    }

    public void restoreLayout() {
        showStatusLayout(contentLayout);
    }

}
