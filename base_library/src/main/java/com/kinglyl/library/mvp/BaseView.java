package com.kinglyl.library.mvp;

import com.kinglyl.library.account.Account;

public interface BaseView {

    Account getAccount();

    /**
     * 显示加载中
     */
    void showLoading();

    /**
     * 隐藏加载
     */
    void closeLoading();

    /**
     * 显示错误信息
     */
    void onHttpError(int code, String message);
}
