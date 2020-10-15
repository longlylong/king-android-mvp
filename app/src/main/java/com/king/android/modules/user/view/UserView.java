package com.king.android.modules.user.view;

import com.king.android.modules.user.dto.LoginInfoDto;
import com.simga.library.base.BaseView;

public interface UserView extends BaseView {

    void loginSuccess(LoginInfoDto result);
}
