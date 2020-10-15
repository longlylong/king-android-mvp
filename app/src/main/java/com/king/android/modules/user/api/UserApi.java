package com.king.android.modules.user.api;

import com.king.android.modules.user.dto.LoginInfoDto;
import com.king.android.modules.user.dto.LoginVo;
import com.simga.library.http.bean.BaseResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {

    @POST("web/backend/sys/loginSys")
    Call<BaseResult<LoginInfoDto>> loginSys(@Body LoginVo vo);
}
