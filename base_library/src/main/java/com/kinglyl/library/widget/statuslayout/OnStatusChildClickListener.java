package com.kinglyl.library.widget.statuslayout;

import android.view.View;

public interface OnStatusChildClickListener {

    /**
     * 空数据布局子 View 被点击
     */
    void onEmptyChildClick(View view);

    /**
     * 出错布局子 View 被点击
     */
    void onErrorChildClick(View view);

    /**
     * 自定义状态布局布局子 View 被点击
     */
    void onCustomerChildClick(View view);
}
