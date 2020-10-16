package com.kinglyl.android.modules.user.view;

import com.kinglyl.android.modules.user.dto.LoginInfoDto;
import com.simga.library.base.BaseView;

public interface UserView extends BaseView {

    void loginSuccess(LoginInfoDto result);
}
