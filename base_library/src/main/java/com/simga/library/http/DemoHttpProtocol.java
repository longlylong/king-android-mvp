package com.simga.library.http;

import com.simga.library.http.bean.BaseRequest;
import com.simga.library.http.bean.BaseResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface DemoHttpProtocol {

//    @GET("/authorize/get")
//    public void getAuthronize(@Query("loginId") long loginId,
//                              @Query("token") String token, Callback<BaseRequestBean<AuthorizationBean>> callback);
//
//    @Multipart
//    @POST("/authorize/upload")
//    public void submitAuthronize(@Part("loginId") long loginId,
//                                 @Part("token") String token, @Part("certificate") TypedFile file, Callback<BaseRequestBean<?>> callback);
    //表单提交
//    @FormUrlEncoded
//    @POST("/user/advice")
//    public void submitFeedBack(@Field("loginId") long loginId,
//                               @Field("token") String token, @Field("qnVersion") String qnVersion
//            , @Field("mobileNum") String mobileNum, @Field("systemVersion") String systemVersion, @Field("email") String email,
//                               @Field("content") String content
//            , Callback<BaseRequestBean<?>> callback);

    //json post
//    @POST("index/quto")
//    Call<MarkPriceBean> getMarkPriceAll(@Body BaseRequestBean bean);

    @POST("xx/xx")
    Call<BaseResult> getMarkPriceAll(@Body BaseRequest bean);

}
