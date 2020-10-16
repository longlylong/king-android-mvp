package com.kinglyl.android.modules.user.view;

import com.kinglyl.android.modules.user.dto.LoginInfoDto;
import com.kinglyl.library.mvp.BaseView;

public interface UserView extends BaseView {

    void loginSuccess(LoginInfoDto result);
}
