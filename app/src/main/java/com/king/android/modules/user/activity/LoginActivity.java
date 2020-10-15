package com.king.android.modules.user.activity;

import android.os.Bundle;

import com.king.android.R;
import com.king.android.modules.user.dto.LoginInfoDto;
import com.king.android.modules.user.persenter.UserPresenter;
import com.king.android.modules.user.view.UserView;
import com.simga.library.activity.BaseActivity;
import com.simga.library.base.CreatePresenter;

@CreatePresenter(presenter = UserPresenter.class)
public class LoginActivity extends BaseActivity<UserPresenter> implements UserView {

    public static void open(BaseActivity<?> mContext) {
        mContext.startActivity(LoginActivity.class);
    }

    @Override
    protected int getViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initBundle(Bundle bundle) {

    }

    @Override
    protected void initUI(Bundle savedInstanceState) {
        setTitle("登录");
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {
    }

    @Override
    public void loginSuccess(LoginInfoDto result) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().userLogin("13413513467", "123456");
    }
}
