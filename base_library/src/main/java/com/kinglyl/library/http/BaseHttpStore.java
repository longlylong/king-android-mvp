package com.kinglyl.library.http;

import com.blankj.utilcode.util.LogUtils;
import com.kinglyl.library.http.bean.BaseResult;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class BaseHttpStore {

    public String getSyn(String url) {
        return request(url, "GET", null);
    }

    public String postFormSyn(String url, String param) {
        return request(url, "POST_FORM", param);
    }

    public String postJsonSyn(String url, String jsonBody) {
        return request(url, "POST", jsonBody);
    }

    private String request(String url, String method, String body) {
        OkHttpClient client = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        builder.url(url);

        if ("GET".equals(method)) {
            builder.get();
        } else if ("POST".equals(method)) {
            builder.addHeader("Content-Type", "application/json;charset=UTF-8");
            builder.post(RequestBody.create(MediaType.parse("text/json"), body));
        } else if ("POST_FORM".equals(method)) {
            builder.addHeader("Content-Type", "application/x-www-form-urlencoded");
            builder.post(RequestBody.create(MediaType.parse("text/*"), body));
        }

        Call call = client.newCall(builder.build());
        try {
            Response response = call.execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private boolean reLogin() throws IOException {
//        Account account = AccountManager.getInstance().getAccount();
//        LoginRequestBean bean = new LoginRequestBean();
//        bean.phone = account.phone;
//        bean.password = account.psw;
//
//        UserHttpProtocol userHttpProtocol = HttpProtocolFactory.getProtocol(HttpConstant.getRootApiUrl(), UserHttpProtocol.class);
//        retrofit2.Call<LoginBean> login = userHttpProtocol.login(bean);
//        retrofit2.Response<LoginBean> response = login.execute();
//        LoginBean loginBean = response.body();
//        if (loginBean != null && loginBean.ok()) {
//            account.token = loginBean.data.token;
//            AccountManager.getInstance().saveAccount(account);
//            return true;
//        } else {
//            return false;
//        }
        return false;
    }

    protected <T> BaseResult<T> getResult(retrofit2.Call<BaseResult<T>> call) {
        try {
            retrofit2.Response<BaseResult<T>> response = call.execute();
            if (response.isSuccessful()) {
                BaseResult<T> body = response.body();
                if (body != null) {
                    String msg = body.message;
                    LogUtils.e(response.raw().request().url().toString() + " --> " + msg);

                    //token失效处理
//                    if (body.code == 10001) {
//                        if (reLogin()) {
//                            return getResult(call.clone());
//                        } else {
//                            return null;
//                        }
//                    }
                }
                return response.body();
            } else {
                ResponseBody errorB = response.errorBody();
                if (errorB != null) {
                    LogUtils.d(errorB.toString());
                }
                return null;
            }
        } catch (Exception e) {
            LogUtils.d("异常 --> " + call.request().url().toString());
            LogUtils.d(e.getMessage());
        }
        return null;
    }


}
