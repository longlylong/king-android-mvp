package com.kinglyl.library.http;

import com.blankj.utilcode.util.GsonUtils;
import com.kinglyl.library.utils.LogUtil;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

public class DecodeRequestBodyConverter<T> implements Converter<T, RequestBody> {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    @Override
    public RequestBody convert(T value) {
        String toJson = GsonUtils.toJson(value);
        LogUtil.e("请求参数:");
        LogUtil.e(toJson);
        return RequestBody.create(MEDIA_TYPE, toJson);
    }
}
