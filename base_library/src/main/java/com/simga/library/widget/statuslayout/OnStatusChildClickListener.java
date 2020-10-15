package com.simga.library.widget.statuslayout;
import android.view.View;

/**
 * 作者：SimGa
 * 日期：2019/4/29
 * 时间：14:20
 * 包名：com.simga.baselibrary.widget.statuslayout
 */
public interface OnStatusChildClickListener {

    /**
     * 空数据布局子 View 被点击
     *
     * @param view 被点击的 View
     * @since v1.0.0
     */
    void onEmptyChildClick(View view);

    /**
     * 出错布局子 View 被点击
     *
     * @param view 被点击的 View
     * @since v1.0.0
     */
    void onErrorChildClick(View view);

    /**
     * 自定义状态布局布局子 View 被点击
     *
     * @param view 被点击的 View
     * @since v1.0.0
     */
    void onCustomerChildClick(View view);
}
